package com.braineer.scheduler

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.projitize.apcodelearner.viewmodels.LoginViewModel
import com.projitize.apcodelearner.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentRegistrationBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(requireActivity())



        binding.signup.setOnClickListener {

            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPass.text.toString()
            val name: String = binding.etName.text.toString()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                if (name.isEmpty()) {
                    binding.editTextNameLayout.error = "*Name required"
                    binding.etName.requestFocus()
                } else {
                    binding.editTextNameLayout.isErrorEnabled = false
                }
                if (email.isEmpty()) {
                    binding.editTextEmailLayout.error = "*Email required"
                    binding.etEmail.requestFocus()
                } else {
                    binding.editTextEmailLayout.isErrorEnabled = false
                }
                if (password.isEmpty()) {
                    binding.editTextPassLayout.error = "*Password required"
                    binding.etPass.requestFocus()
                } else {
                    binding.editTextPassLayout.isErrorEnabled = false
                }
            } else if (password.length < 6) {
                binding.editTextPassLayout.error = "*Password length must be 6"
                binding.etPass.requestFocus()
            } else if (!(email.isEmpty() && password.isEmpty())) {

                binding.editTextEmailLayout.isErrorEnabled = false
                binding.editTextPassLayout.isErrorEnabled = false
                binding.editTextNameLayout.isErrorEnabled = false

                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.setTitle("Creating Account")
                progressDialog.setMessage("Please wait a moment. We are creating your account")
                progressDialog.show()

                val progressbar = progressDialog.findViewById(android.R.id.progress) as ProgressBar
                progressbar.indeterminateDrawable.setColorFilter(Color.parseColor("#F75022"), android.graphics.PorterDuff.Mode.SRC_IN)

                loginViewModel.registerUser(email,password,name){
                    if (it=="Success"){
                        progressDialog.dismiss()
                    }else{
                        progressDialog.dismiss()
                    }
                }


            }


        }


        setPrivacyPolicyText(binding.tvTermsCondition)

        return binding.root
    }

    private fun setPrivacyPolicyText(view: TextView) {
        val spanTxt = SpannableStringBuilder(
            "By signing up, you are accepting our "
        )
        spanTxt.append("Privacy Policy")
        spanTxt.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                //startActivity(new Intent(getActivity(), TermsCondition.class));
            }
        }, spanTxt.length - "Privacy Policy".length, spanTxt.length, 0)
        spanTxt.append(" and")
        //spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 32, spanTxt.length(), 0);
        spanTxt.append(" Terms & Condition")
        spanTxt.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // startActivity(new Intent(RegistrationActivity.this, TermsCondition.class));
            }
        }, spanTxt.length - " Terms & Condition".length, spanTxt.length, 0)
        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(spanTxt, TextView.BufferType.SPANNABLE)
        view.gravity = Gravity.CENTER
    }

}