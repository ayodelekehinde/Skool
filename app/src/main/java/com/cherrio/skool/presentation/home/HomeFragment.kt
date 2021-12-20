package com.cherrio.skool.presentation.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.cherrio.skool.R
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentHomeBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.presentation.FooterAdapter
import com.cherrio.skool.presentation.details.CourseDetailFragmentArgs
import com.cherrio.skool.presentation.filter.FilterDialog
import com.cherrio.skool.presentation.tab.TabsFragmentDirections
import com.cherrio.skool.utils.consume
import com.cherrio.skool.utils.findNavControllerById
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 12/7/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    private val coursesAdapter = CoursesAdapter {
        gotoDetails(it)
    }

    override fun getBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun useViews() {
        binding.apply {
            courseList.adapter = coursesAdapter.withLoadStateFooter(
                FooterAdapter(coursesAdapter::retry)
            )
            btnRetry.setOnClickListener {
                coursesAdapter.refresh()
            }
            btnFavs.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionTabHomeToTabSaved())
            }
        }
        coursesAdapter.addLoadStateListener { loadstate ->
            handleState(loadstate.source)
        }
        viewModel.state.consume(viewLifecycleOwner) { state ->
            state.prefs?.let {
               if (!it.isSet){
                   findNavControllerById(R.id.main_navigation_fragment)
                       .navigate(TabsFragmentDirections.actionTabsFragmentToWelcomeFragment())
               }else{
                   viewModel.getPaginatedData(it).consume(viewLifecycleOwner){ pagingData ->
                       coursesAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                   }
               }
            }
        }


    }

    private fun handleState(states: LoadStates) {
        when (states.refresh) {
            is LoadState.Loading ->{
                if (coursesAdapter.itemCount == 0) binding.loading.isVisible = true
                binding.apply {
                    offlineMsg.isVisible = false
                    btnFavs.isVisible = false
                    btnRetry.isVisible = false
                }
            }
            is LoadState.Error -> {
                val error = (states.refresh as LoadState.Error).error.localizedMessage!!
                println("Error: $error")
                binding.loading.isVisible = false
                binding.apply {
                    offlineMsg.isVisible = true
                    btnFavs.isVisible = true
                    btnRetry.isVisible = true
                }
            }
            is LoadState.NotLoading -> {
                binding.loading.isVisible = false
            }
        }
    }

    private fun gotoDetails(course: Course) {
        findNavControllerById(R.id.main_navigation_fragment)
            .navigate(TabsFragmentDirections.actionTabsFragmentToCourseDetailFragment(course))
    }

    override fun useState() {}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_filter) {
            openFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFilterDialog() {
        FilterDialog().show(childFragmentManager, FilterDialog.TAG)
    }
}