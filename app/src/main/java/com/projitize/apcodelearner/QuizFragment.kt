package com.projitize.apcodelearner

import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.QuizAdapter
import com.projitize.apcodelearner.databinding.AddQuizItemDialogBinding
import com.projitize.apcodelearner.databinding.FragmentQuizBinding
import com.projitize.apcodelearner.databinding.QuizScoreShowDialogBinding
import com.projitize.apcodelearner.models.QuizModel
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var adapter: QuizAdapter
    private var answerList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        adapter = QuizAdapter{ position, binding, model->

            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selectedIndex = binding.radioGroup.indexOfChild(binding.root.findViewById<RadioButton>(checkedId))

                if (selectedIndex >= 0 && model.answer?.toInt() == selectedIndex + 1) {
                    if (!answerList.contains(model.answer)){
                        answerList.add(model.answer!!)
                    }
                }else{
                    if (answerList.contains(model.answer)){
                        answerList.remove(model.answer)
                    }
                }
            }


        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.quizRecycler.layoutManager = llm
        binding.quizRecycler.adapter = adapter


        getQuizList()

        binding.btnFinish.setOnClickListener{

            val dialogBuilder = AlertDialog.Builder(activity)

            val binding = QuizScoreShowDialogBinding.inflate(LayoutInflater.from(requireActivity()))
            dialogBuilder.setView(binding.root)

            val alertDialog = dialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding.scoreText.text = "You have scored ${answerList.size} out of ${adapter.itemCount}"

            binding.btnOk.setOnClickListener {
                alertDialog.dismiss()
                findNavController().popBackStack()
            }

        }

        return  binding.root
    }

    private fun getQuizList() {
        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getQuizItemList().observe(viewLifecycleOwner) { list ->

                if (list.isEmpty()) {
                    binding.mProgressBar.visibility = View.GONE
                    binding.empty.visibility = View.VISIBLE
                } else {
                    binding.mProgressBar.visibility = View.GONE
                    binding.empty.visibility = View.GONE
                }
                adapter.submitList(list)
            }
        }
    }

}