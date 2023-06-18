package com.projitize.apcodelearner

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projitize.apcodelearner.databinding.FragmentAboutBinding
import com.projitize.apcodelearner.viewmodels.LoginViewModel


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    var isExpand = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAboutBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        loginViewModel.authStateLD.observe(viewLifecycleOwner) {
            if (it != LoginViewModel.AuthState.AUTHENTICATED) {
                findNavController().navigate(R.id.action_aboutFragment_to_logRegFragment)
            }
        }

        binding.cardAp.setOnClickListener {

            if (isExpand){
                binding.tvAbout.visibility = View.GONE
            }else{
                binding.tvAbout.visibility = View.VISIBLE
            }
            isExpand=!isExpand
        }


        binding.cardFeedback.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_feedbackFragment)
        }


        binding.cardLogout.setOnClickListener {

            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Logout Alert!")
            builder.setMessage("Are you sure want to logout?")
            builder.setCancelable(false)

            builder.setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(requireActivity(),
                    "Logged out", Toast.LENGTH_SHORT).show()
                loginViewModel.logout()
            }

            builder.setNegativeButton("No") { dialog, which ->

            }

            builder.show()

        }


        return binding.root
    }
}