package com.projitize.apcodelearner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.MiniProjectAdapter
import com.projitize.apcodelearner.databinding.FragmentMiniProjectBinding
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class MiniProjectFragment : Fragment() {

    private lateinit var binding:FragmentMiniProjectBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMiniProjectBinding.inflate(inflater, container, false)


        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = MiniProjectAdapter{ position, binding, model->

            binding.cardItem.setOnClickListener {
                val bottomSheet = ProjectDetailsBottomSheetFragment(model)
                bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
            }

        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.projectRecycler.layoutManager = llm
        binding.projectRecycler.adapter = adapter


        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getMiniProjectList().observe(viewLifecycleOwner) { list ->

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