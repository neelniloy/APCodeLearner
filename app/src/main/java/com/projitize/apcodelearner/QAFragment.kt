package com.projitize.apcodelearner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.QAAdapter
import com.projitize.apcodelearner.adapters.ReferenceAdapter
import com.projitize.apcodelearner.databinding.FragmentQABinding
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class QAFragment : Fragment() {

    private lateinit var binding: FragmentQABinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val adminViewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQABinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = QAAdapter{ position, binding, model->


        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.qaRecycler.layoutManager = llm
        binding.qaRecycler.adapter = adapter


        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getQaList().observe(viewLifecycleOwner) { list ->

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