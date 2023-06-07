package com.braineer.shromikseba

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.braineer.shromikseba.databinding.ActivityMainBinding
import com.braineer.shromikseba.utils.TAG
import com.braineer.shromikseba.viewmodels.AdminViewModel
import com.braineer.shromikseba.viewmodels.LoginViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val loginViewModel: LoginViewModel by viewModels()
    private val adminViewModel: AdminViewModel by viewModels()

    private var mInterstitialAd: InterstitialAd? = null
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable = Runnable { }
    private var adShow = false

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        setSupportActionBar(binding.mToolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNav,navController)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        // hiding bottom bar
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            // the IDs of fragments as defined in the `navigation_graph`
            if (nd.id == R.id.homeFragment || nd.id == R.id.sebaFragment
                || nd.id == R.id.menuFragment
            ) {
                binding.bottomNav.visibility = View.VISIBLE
            } else {
                binding.bottomNav.visibility = View.GONE
            }

            //show ads
            if (mInterstitialAd != null) {
                showProgressDialog()
                Handler(Looper.getMainLooper()).postDelayed({
                    hideProgressDialog()
                    mInterstitialAd?.show(this)
                    mInterstitialAd=null
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable,180*1000L)
                    adShow = true
                }, 1000)


            } else {
                hideProgressDialog()
            }
        }

        //set open alert
        adminViewModel.getOpenAlert().observe(this){

            if (it.show!!){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("ঘোষণা!")
                builder.setIcon(R.drawable.logo_round)
                builder.setMessage(it.des)

                builder.setPositiveButton("ঠিক আছে") { dialog, which ->

                }

                builder.show()
            }
        }

        //Admob Ads

        MobileAds.initialize(this) {
            //scheduleAd()
        }
        val adRequest = AdRequest.Builder().build()

        runnable= Runnable {
            InterstitialAd.load(this,getString(R.string.interstitial_ad_id), adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    handler.removeCallbacks(runnable)
                }
            })
        }

        adminViewModel.getAdmobStatus().observe(this@MainActivity){status->
            if (status){
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,200*1000L)

                val adRequest = AdRequest.Builder().build()
                binding.adView.loadAd(adRequest)
                binding.adView.isVisible = true
            }else{
                handler.removeCallbacks(runnable)
                mInterstitialAd = null
                binding.adView.destroy()
                binding.adView.isVisible = false
            }

            adShow = status
        }

    }

    @SuppressLint("HardwareIds")
    override fun onResume() {
        super.onResume()
        loginViewModel.firebaseAuth.currentUser?.let {
            loginViewModel.updateOnlineStatus(true)
        }

    }

    override fun onStop() {
        super.onStop()
        loginViewModel.firebaseAuth.currentUser?.let {
            loginViewModel.updateLastAppExitTimeAndOnlineStatus(
                time = System.currentTimeMillis(),
                status = false
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        if(navController.graph.startDestinationId != navController.currentDestination?.id){

            if (mInterstitialAd != null) {
                showProgressDialog()
                Handler(Looper.getMainLooper()).postDelayed({
                    hideProgressDialog()
                    mInterstitialAd?.show(this)
                    mInterstitialAd=null
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable,180*1000L)
                    adShow = true
                }, 1000)
                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            } else {
                hideProgressDialog()
                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            }
        }else{
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }

    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        mInterstitialAd=null
        super.onDestroy()
    }

    // Function to show the progress dialog
    private fun showProgressDialog() {
        // Check if progress dialog is already showing
        if (progressDialog == null) {
            // Create a new progress dialog
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage("Loading Ads...") // Set the message to display
            progressDialog!!.setCancelable(false) // Set dialog to be non-cancelable
            progressDialog!!.show() // Show the progress dialog
        }
    }

    // Function to hide the progress dialog
    private fun hideProgressDialog() {
        // Check if progress dialog is currently showing
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss() // Hide the progress dialog
            progressDialog = null // Set the dialog variable to null
        }
    }
}