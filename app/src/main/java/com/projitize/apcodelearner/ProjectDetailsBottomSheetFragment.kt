package com.projitize.apcodelearner

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projitize.apcodelearner.databinding.ProjectDetailsBottomSheetBinding
import com.projitize.apcodelearner.models.MiniProjectModel
import com.projitize.apcodelearner.viewmodels.AdminViewModel
import com.projitize.apcodelearner.viewmodels.UserViewModel

class ProjectDetailsBottomSheetFragment(val model: MiniProjectModel) : BottomSheetDialogFragment() {

    lateinit var binding: ProjectDetailsBottomSheetBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val adminViewModel: AdminViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.BottomSheetDialogStyle)
        binding = ProjectDetailsBottomSheetBinding.inflate(inflater, container, false)

        binding.root.setBackgroundResource(R.drawable.rounded_dialog)

        binding.title.text = model.title
        binding.code.text = model.code
        binding.output.text = model.output
        binding.extra.text = model.extra

        binding.codeCard.setOnClickListener {

            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", model.code)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(requireActivity(), "Code copied to the clipboard", Toast.LENGTH_SHORT).show()

        }


        return binding.root

    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

}