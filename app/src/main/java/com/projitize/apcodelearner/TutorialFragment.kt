package com.projitize.apcodelearner

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projitize.apcodelearner.adapters.MiniProjectAdapter
import com.projitize.apcodelearner.adapters.TutorialAdapter
import com.projitize.apcodelearner.databinding.FragmentMiniProjectBinding
import com.projitize.apcodelearner.databinding.FragmentTutorialBinding
import com.projitize.apcodelearner.models.TutorialModel
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel


class TutorialFragment : Fragment() {

    private lateinit var binding: FragmentTutorialBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentTutorialBinding.inflate(inflater, container, false)


        (activity as AppCompatActivity).supportActionBar!!.show()

        val adapter = TutorialAdapter{ position, binding, model->

            binding.cardItem.setOnClickListener {
                val bottomSheet = TutorialDetailsBottomSheetFragment(model)
                bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
            }

        }

        val llm = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.tutorialRecycler.layoutManager = llm
        binding.tutorialRecycler.adapter = adapter


        userViewModel.getCurrentUserId()?.let {
            adminViewModel.getTutorialList().observe(viewLifecycleOwner) { list ->

                val tempList = mutableListOf<TutorialModel>()

                if (list.isEmpty()) {
                    binding.mProgressBar.visibility = View.GONE
                    binding.empty.visibility = View.VISIBLE
                } else {
                    binding.mProgressBar.visibility = View.GONE
                    binding.empty.visibility = View.GONE

                    tempList.clear()
                    var count = 0
                    for (item in list) {
                        count++
                        item.count = count
                        tempList.add(item)
                    }
                    adapter.submitList(tempList)
                    adapter.notifyDataSetChanged()
                }

                binding.searchBar.addTextChangedListener (object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        tempList.clear()

                        if (s!!.isEmpty()) {
                            var count = 0
                            for (item in list) {
                                count++
                                item.count = count
                                tempList.add(item)
                            }
                            adapter.submitList(tempList)
                            adapter.notifyDataSetChanged()
                        }else{
                            var count = 0
                            for (item in list) {
                                if(item.title!!.contains(s) || item.title!!.lowercase().contains(s)){
                                    count++
                                    item.count = count
                                    tempList.add(item)
                                }
                            }
                            adapter.submitList(tempList)
                            adapter.notifyDataSetChanged()
                        }
                    }
                })

            }
        }


        return binding.root
    }

}