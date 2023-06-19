package com.projitize.apcodelearner

import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projitize.apcodelearner.databinding.AddFeedbackDialogBinding
import com.projitize.apcodelearner.databinding.AddQuizItemDialogBinding
import com.projitize.apcodelearner.databinding.FragmentAboutBinding
import com.projitize.apcodelearner.models.FeedbackModel
import com.projitize.apcodelearner.models.QuizModel
import com.projitize.apcodelearner.viewmodels.LoginViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    var isExpand = false
    private var name = ""

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

        userViewModel.getUser(userViewModel.getCurrentUserId()!!).observe(viewLifecycleOwner){

           name = it.userName!!

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

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddFeedbackDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



            binding.btnOk.setOnClickListener {
                val feedback = binding.etFeedback.text.toString().trim()

                if ( feedback.isEmpty()) {

                    Toast.makeText(
                        requireActivity(),
                        "Field can not be empty",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val progressDialog = ProgressDialog(requireActivity())
                    progressDialog.setMessage("Please wait...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    val progressbar =
                        progressDialog.findViewById(android.R.id.progress) as ProgressBar
                    progressbar.indeterminateDrawable.setColorFilter(
                        Color.parseColor("#F75022"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    val model = FeedbackModel(
                        feedback = feedback,
                        name = name,
                        time = System.currentTimeMillis()
                    )

                    userViewModel.addFeedback(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "Feedback sent successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(requireActivity(), "Something went wrong, Please try again", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

            binding.btnCancel.setOnClickListener { alertDialog.dismiss() }


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