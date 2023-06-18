package com.projitize.apcodelearner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.projitize.apcodelearner.adapters.ReferenceAdapter
import com.projitize.apcodelearner.databinding.FragmentReferenceBinding
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class ReferenceFragment : Fragment() {

    private lateinit var binding:FragmentReferenceBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val adminViewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReferenceBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = ReferenceAdapter{ position, binding, model->


        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.refRecycler.layoutManager = llm
        binding.refRecycler.adapter = adapter


        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getReferenceList().observe(viewLifecycleOwner) { list ->

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