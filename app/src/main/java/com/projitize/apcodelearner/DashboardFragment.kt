package com.projitize.apcodelearner

import CustomAlertDialog
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.projitize.apcodelearner.databinding.FragmentDashboardBinding
import com.projitize.apcodelearner.viewmodels.LoginViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentDashboardBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.hide()

        userViewModel.getUser(userViewModel.getCurrentUserId()!!).observe(viewLifecycleOwner){

            binding.userName.text = it.userName
            binding.userEmail.text = it.emailAddress

            //show admin panel
            if (it.userType=="admin"){
                binding.cardAdminPanel.visibility = View.VISIBLE
            }else{
                binding.cardAdminPanel.visibility = View.GONE
            }

        }

        binding.cardTutorial.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_tutorialFragment)
        }

        binding.cardQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_quizFragment)
        }

        binding.cardMiniProject.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_miniProjectFragment)
        }

        binding.cardQa.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_QAFragment)
        }

        binding.cardAbout.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_aboutFragment)
        }

        binding.cardReference.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_referenceFragment)
        }

        binding.cardAdminPanel.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_adminFragment)
        }



        return binding.root
    }

}