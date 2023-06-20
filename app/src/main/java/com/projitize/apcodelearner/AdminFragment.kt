package com.projitize.apcodelearner

import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projitize.apcodelearner.databinding.AddMiniProjectDialogBinding
import com.projitize.apcodelearner.databinding.AddQaDialogBinding
import com.projitize.apcodelearner.databinding.AddQuizItemDialogBinding
import com.projitize.apcodelearner.databinding.AddReferenceDialogBinding
import com.projitize.apcodelearner.databinding.AddTutorialDialogBinding
import com.projitize.apcodelearner.databinding.FragmentAdminBinding
import com.projitize.apcodelearner.models.*
import com.projitize.apcodelearner.viewmodels.AdminViewModel


class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private val adminViewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAdminBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()


        binding.cardQa.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddQaDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            binding.btnOk.setOnClickListener {
                val question = binding.etQuestion.text.toString().trim()
                val answer = binding.etAnswer.text.toString().trim()


                if (question.isEmpty() || answer.isEmpty()) {

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
                        progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                    progressbar.indeterminateDrawable.setColorFilter(
                        Color.parseColor("#F75022"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    val model = QaModel(
                        question = question,
                        answer = answer,
                        time = System.currentTimeMillis()
                    )

                    adminViewModel.addQA(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "QnA added successfully",
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


        binding.cardReference.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddReferenceDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            binding.btnOk.setOnClickListener {
                val title = binding.etTitle.text.toString().trim()
                val link = binding.etLink.text.toString().trim()


                if (title.isEmpty() || link.isEmpty()) {

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
                        progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                    progressbar.indeterminateDrawable.setColorFilter(
                        Color.parseColor("#F75022"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    val model = ReferenceModel(
                        title = title,
                        link = link,
                        time = System.currentTimeMillis()
                    )

                    adminViewModel.addReference(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "Reference added successfully",
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


        binding.cardMiniProject.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddMiniProjectDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            binding.btnOk.setOnClickListener {
                val title = binding.etTitle.text.toString().trim()
                val code = binding.etCode.text.toString().trim()
                val output = binding.etOutput.text.toString().trim()
                val extra = binding.etExtra.text.toString().trim()


                if (title.isEmpty() || code.isEmpty() || output.isEmpty()) {

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
                        progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                    progressbar.indeterminateDrawable.setColorFilter(
                        Color.parseColor("#F75022"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    val model = MiniProjectModel(
                        title = title,
                        code = code,
                        output = output,
                        extra = extra.ifEmpty { " " },
                        time = System.currentTimeMillis()
                    )

                    adminViewModel.addMiniProject(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "Project added successfully",
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

        binding.cardQuiz.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddQuizItemDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //drop-down
            val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")

            val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_dropdown_item_1line, items)
            binding.spinnerAnswer.setAdapter(adapter)

            var answer = ""
            binding.spinnerAnswer.setOnItemClickListener { _, _, position, _ ->

                 answer = (position+1).toString()
            }

            binding.btnOk.setOnClickListener {
                val question = binding.etQuestion.text.toString().trim()
                val option1 = binding.option1.text.toString().trim()
                val option2 = binding.option2.text.toString().trim()
                val option3 = binding.option3.text.toString().trim()
                val option4 = binding.option4.text.toString().trim()


                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || answer.isEmpty()) {

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

                    val model = QuizModel(
                        question = question,
                        option1 = option1,
                        option2 = option2,
                        option3 = option3,
                        option4 = option4,
                        answer = answer,
                        time = System.currentTimeMillis()
                    )

                    adminViewModel.addQuizItem(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "Quiz Item added successfully",
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


        binding.cardFeedback.setOnClickListener {

            findNavController().navigate(R.id.action_adminFragment_to_feedbackFragment)

        }

        binding.cardTutorial.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = AddTutorialDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)


            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            binding.btnOk.setOnClickListener {
                val title = binding.etTitle.text.toString().trim()
                val code = binding.etCode.text.toString().trim()
                val details = binding.etDetails.text.toString().trim()


                if (title.isEmpty() || details.isEmpty()) {

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
                        progressDialog!!.findViewById(android.R.id.progress) as ProgressBar
                    progressbar.indeterminateDrawable.setColorFilter(
                        Color.parseColor("#F75022"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    val model = TutorialModel(
                        title = title,
                        code = code.ifEmpty { "" },
                        details = details,
                        time = System.currentTimeMillis()
                    )

                    adminViewModel.addTutorial(model) {
                        if (it == "Success") {
                            progressDialog.dismiss()
                            alertDialog.dismiss()

                            Toast.makeText(
                                requireActivity(),
                                "Tutorial added successfully",
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


        return binding.root
    }

}