package com.projitize.apcodelearner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.projitize.apcodelearner.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    var isExpand = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAboutBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar!!.show()


        binding.cardScheduler.setOnClickListener {

            if (isExpand){
                binding.tvAbout.visibility = View.GONE
            }else{
                binding.tvAbout.visibility = View.VISIBLE
            }
            isExpand=!isExpand
        }


        return binding.root
    }
}