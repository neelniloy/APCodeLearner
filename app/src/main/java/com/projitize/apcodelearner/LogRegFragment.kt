package com.braineer.scheduler

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.projitize.apcodelearner.viewmodels.LoginViewModel
import com.projitize.apcodelearner.R
import com.projitize.apcodelearner.databinding.FragmentLogRegBinding


class LogRegFragment : Fragment() {

    private lateinit var binding: FragmentLogRegBinding
    private val NUM_PAGES = 2
    private lateinit var progressDialog: ProgressDialog
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogRegBinding.inflate(inflater, container, false)

        loginViewModel.authStateLD.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthState.AUTHENTICATED) {
                findNavController().navigate(R.id.action_logRegFragment_to_dashboardFragment)
            }
        }
        loginViewModel.errMsgLD.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        (activity as AppCompatActivity).supportActionBar!!.hide()


        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = LogRegAdapter(requireActivity())
        binding.viewpager.adapter = pagerAdapter


        binding.singIn.setOnClickListener {
            binding.viewpager.currentItem = 0
        }

        binding.signUp.setOnClickListener {
            binding.viewpager.currentItem = 1
        }


        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.viewpager.currentItem = 0
                    binding.singIn.setTextColor(Color.parseColor("#F75022"))
                    binding.signUp.setTextColor(Color.parseColor("#474747"))
                }
                else if (position == 1) {
                    binding.viewpager.currentItem = 1
                    binding.singIn.setTextColor(Color.parseColor("#474747"))
                    binding.signUp.setTextColor(Color.parseColor("#F75022"))
                }
                super.onPageSelected(position)
            }
        })

        return binding.root

    }



    private inner class LogRegAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment()
                1 -> RegistrationFragment()
                else -> {
                    LoginFragment()
                }
            }
        }
    }

}