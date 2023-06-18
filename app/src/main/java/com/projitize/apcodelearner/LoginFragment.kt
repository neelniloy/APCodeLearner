package com.braineer.scheduler

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.projitize.apcodelearner.viewmodels.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.projitize.apcodelearner.R
import com.projitize.apcodelearner.databinding.FragmentLoginBinding
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var progressDialog: ProgressDialog
    private val loginViewModel: LoginViewModel by activityViewModels()
    val Req_Code: Int = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        progressDialog = ProgressDialog(requireActivity())

        FirebaseApp.initializeApp(requireActivity())


        val sharedPreference =  requireActivity().getSharedPreferences("CheckLogin", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        val saveLogin = sharedPreference.getBoolean("saveLogin",false)


        if (saveLogin) {
            binding.etEmail.setText(sharedPreference.getString("user_email", ""))
            binding.etPass.setText(sharedPreference.getString("user_password", ""))
            binding.saveLoginCheckBox.isChecked = true
        }


        binding.login.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()
            // TODO: validate fields

            if (binding.saveLoginCheckBox.isChecked()) {
                editor.putBoolean("saveLogin", true)
                editor.putString("user_email", email)
                editor.putString("user_password", password)
                editor.apply()
            } else {
                editor.clear()
                editor.commit()
            }

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    binding.editTextEmailLayout.setError("*Email required")
                    binding.etEmail.requestFocus()
                } else {
                    binding.editTextEmailLayout.setErrorEnabled(false)
                }
                if (password.isEmpty()) {
                    binding.editTextPassLayout.setError("*Password required")
                    binding.etPass.requestFocus()
                } else {
                    binding.editTextPassLayout.setErrorEnabled(false)
                }
            } else if (!(email.isEmpty() && password.isEmpty())) {


                binding.editTextEmailLayout.setErrorEnabled(false)
                binding.editTextPassLayout.setErrorEnabled(false)

                progressDialog.setMessage("Logging in...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val progressbar = progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                progressbar.indeterminateDrawable.setColorFilter(Color.parseColor("#F75022"), android.graphics.PorterDuff.Mode.SRC_IN)


                loginViewModel.loginUser(email, password) {
                    if (it == "Success") {
                        progressDialog.dismiss()
                    } else {
                        progressDialog.dismiss()
                    }
                }

            }

        }



        //Forgot Password Dialog
        binding.tvForgotpass.setOnClickListener {

            getForgotDialog()

        }


        return binding.root
    }

    private fun getForgotDialog() {
        val dialogBuilder = AlertDialog.Builder(
            activity
        )

        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_single_edit_dialog, null)
        dialogBuilder.setView(dialogView)


        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val title = dialogView.findViewById<TextView>(R.id.tv_title_dialog)
        val editText = dialogView.findViewById<TextInputEditText>(R.id.et_dialog)
        val editTextLayout = dialogView.findViewById<TextInputLayout>(R.id.editTextDialogLayout)
        val btnOk: MaterialButton = dialogView.findViewById(R.id.btn_ok)
        val btnCancel: MaterialButton = dialogView.findViewById(R.id.btn_cancel)

        title.text = "Enter Your Registered Email"
        btnOk.setText("Send")
        editTextLayout.hint = "Email"
        editText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        btnOk.setOnClickListener {
            val reset_email = editText.text.toString().trim { it <= ' ' }
            if (reset_email.isEmpty()) {
                AestheticDialog.Builder(requireActivity(), DialogStyle.TOASTER, DialogType.ERROR)
                    .setTitle("Empty")
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setMessage("An Email Must be Entered")
                    .show()
            } else {
                progressDialog = ProgressDialog(requireActivity())
                progressDialog.setMessage("You will be sent an email...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val progressbar = progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                progressbar.indeterminateDrawable.setColorFilter(Color.parseColor("#953FFF"), android.graphics.PorterDuff.Mode.SRC_IN)

                Handler().postDelayed({
                    loginViewModel.resetPass(reset_email){

                        if (it == "Success"){

                            progressDialog.dismiss()
                            alertDialog.dismiss()
                            AestheticDialog.Builder(
                                requireActivity(),
                                DialogStyle.TOASTER,
                                DialogType.SUCCESS
                            )
                                .setTitle("Success")
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setMessage("An Email Sent To Your Registered Email")
                                .show()

                        }else{
                            progressDialog.dismiss()
                            AestheticDialog.Builder(
                                requireActivity(),
                                DialogStyle.TOASTER,
                                DialogType.ERROR
                            )
                                .setTitle("Password Reset Failed")
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setMessage("Please Check The Email You Have Entered and Try Again")
                                .show()
                        }

                    }
                }, 1500)
            }
        }

        btnCancel.setOnClickListener { alertDialog.dismiss() }
    }


}