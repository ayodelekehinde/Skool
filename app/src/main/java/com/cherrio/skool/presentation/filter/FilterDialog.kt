package com.cherrio.skool.presentation.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cherrio.skool.databinding.DialogFilterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cherrio.skool.R
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.presentation.pick_course.CategoryAdapter
import com.cherrio.skool.utils.consume
import dagger.hilt.android.AndroidEntryPoint


/**
 *Created by Ayodele on 12/13/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@AndroidEntryPoint
class FilterDialog: DialogFragment() {

    private lateinit var binding: DialogFilterBinding
    private val viewModel by viewModels<FilterViewModel>()
    private val categoryAdapter = CategoryAdapter{ cat, pos ->
        addCategory(pos)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DialogFilterBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog!!.window!!.attributes)
        layoutParams.width = -1
        layoutParams.height = -2
        useViews()
    }

    private fun useViews(){
        binding.apply {
            catList.apply {
                adapter = categoryAdapter
                layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
            }
            btnCancel.setOnClickListener {
                dismissAllowingStateLoss()
            }
            btnApply.setOnClickListener {
                val category = categoryAdapter.getSelectedCategory()
                val prefs = Prefs(category.cat,
                    matPrice.text.toString(),
                    matLevel.text.toString(),
                    matOrder.text.toString(),
                    true
                )
                viewModel.setPrefs(prefs)
                dismissAllowingStateLoss()
            }
        }
        viewModel.state.consume(viewLifecycleOwner){ state ->
            with(state){
                prefs?.let {
                    binding.apply {
                        matPrice.setText(it.price)
                        matLevel.setText(it.learningLevel)
                        matOrder.setText(it.ordering)
                    }
                }
                categories?.let {
                    categoryAdapter.setList(it)
                }
                prices?.let {
                    val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, it)
                    binding.matPrice.setAdapter(adapter)
                }
                levels?.let {
                    val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, it)
                    binding.matLevel.setAdapter(adapter)
                }
                orders?.let{
                    val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, it)
                    binding.matOrder.setAdapter(adapter)
                }

            }
        }
    }
    private fun addCategory(position: Int){
        categoryAdapter.notify(position)
    }

    companion object{
        const val TAG = "FilterDialog"
    }
}