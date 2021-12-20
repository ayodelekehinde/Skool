package com.cherrio.skool.presentation.pick_course

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentWelcomeBinding
import com.cherrio.skool.utils.consume
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 12/7/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@AndroidEntryPoint
class WelcomeFragment: BaseFragment<FragmentWelcomeBinding>() {
    private val viewModel by viewModels<WelcomeViewModel>()
    private val categoryAdapter = CategoryAdapter{ cat, _ ->
        setCategory(cat)
    }
    private fun setCategory(category: Category){
        viewModel.setCategory(category)
    }

    override fun getBinding(inflater: LayoutInflater): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater)
    }

    override fun useViews() {
        binding.apply {
            btnContinue.hide()
            catList.apply {
                adapter = categoryAdapter
                layoutManager = StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.HORIZONTAL)
            }

            btnContinue.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToTabsFragment())
            }
        }

    }

    override fun useState() {
        viewModel.state.consume(viewLifecycleOwner){ state ->
            with(state){
                categories?.let {
                    categoryAdapter.setList(it)
                }
                success?.let {
                    binding.btnContinue.show()
                }
            }
        }
    }
}