package com.projitize.apcodelearner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.QuizAdapter
import com.projitize.apcodelearner.databinding.FragmentQuizBinding
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var quizScore = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = QuizAdapter{ position, binding, model->

            var attendQuiz = false

            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selectedIndex = binding.radioGroup.indexOfChild(binding.root.findViewById<RadioButton>(checkedId))

                if (!attendQuiz && model.answer?.toInt() ==selectedIndex+1){
                    quizScore++
                    attendQuiz = true
                }else{
                    attendQuiz = true
                }

            }


        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.quizRecycler.layoutManager = llm
        binding.quizRecycler.adapter = adapter


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

        binding.btnFinish.setOnClickListener{
            Toast.makeText(requireActivity(), "Your Score is $quizScore", Toast.LENGTH_SHORT).show()
        }

        return  binding.root
    }

}