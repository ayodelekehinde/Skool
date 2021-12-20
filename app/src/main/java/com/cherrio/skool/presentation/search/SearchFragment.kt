package com.cherrio.skool.presentation.search

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.cherrio.skool.R
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentHomeBinding
import com.cherrio.skool.databinding.FragmentSearchBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.presentation.FooterAdapter
import com.cherrio.skool.presentation.MainViewModel
import com.cherrio.skool.presentation.home.CoursesAdapter
import com.cherrio.skool.presentation.tab.TabsFragmentDirections
import com.cherrio.skool.utils.consume
import com.cherrio.skool.utils.findNavControllerById
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 12/7/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel by viewModels<SearchViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val coursesAdapter = CoursesAdapter {
        gotoDetails(it)
    }

    private fun gotoDetails(course: Course) {
        findNavControllerById(R.id.main_navigation_fragment)
            .navigate(TabsFragmentDirections.actionTabsFragmentToCourseDetailFragment(course))
    }

    override fun getBinding(inflater: LayoutInflater): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater)
    }

    override fun useViews() {
        binding.apply {
            courseList.adapter = coursesAdapter.withLoadStateFooter(
                FooterAdapter(coursesAdapter::retry)
            )
        }
        coursesAdapter.addLoadStateListener { loadstate ->
            handleState(loadstate.source)
        }
    }

    private fun handleState(states: LoadStates) {
        when (states.refresh) {
            is LoadState.Loading -> {
                if (coursesAdapter.itemCount > 0){
                    binding.courseList.isVisible = false
                }
                binding.loading.isVisible = true
            }
            is LoadState.Error -> {
                binding.loading.isVisible = false
            }
            is LoadState.NotLoading -> {
                binding.loading.isVisible = false
                binding.courseList.isVisible = true
            }
        }
    }

    override fun useState() {
        mainViewModel.mainState.consume(viewLifecycleOwner) { mainState ->
            with(mainState) {
                searchQuery?.let {
                    viewModel.getPaginatedData(it).consume(viewLifecycleOwner) { pagingData ->
                        coursesAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                    }
                }
            }
        }
    }
}