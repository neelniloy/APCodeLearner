package com.projitize.apcodelearner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.FeedbackAdapter
import com.projitize.apcodelearner.adapters.QAAdapter
import com.projitize.apcodelearner.databinding.FragmentFeedbackBinding
import com.projitize.apcodelearner.databinding.FragmentMiniProjectBinding
import com.projitize.apcodelearner.databinding.FragmentQABinding
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = FeedbackAdapter{ position, binding, model->


        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.feedbackRecycler.layoutManager = llm
        binding.feedbackRecycler.adapter = adapter


        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getFeedbackList().observe(viewLifecycleOwner) { list ->

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

        return binding.root
    }
}