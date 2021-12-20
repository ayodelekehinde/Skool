package com.cherrio.skool.presentation.saved

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cherrio.skool.R
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentFavouritesBinding
import com.cherrio.skool.databinding.FragmentHomeBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.presentation.home.CoursesAdapter
import com.cherrio.skool.presentation.home.HomeViewModel
import com.cherrio.skool.presentation.tab.TabsFragmentDirections
import com.cherrio.skool.utils.consume
import com.cherrio.skool.utils.findNavControllerById
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 12/7/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@AndroidEntryPoint
class FavouritesFragment: BaseFragment<FragmentFavouritesBinding>() {

    private val viewModel by viewModels<FavouritesViewModel>()

    private val coursesAdapter = FavouriteCoursesAdapter{
        gotoDetails(it)
    }

    override fun getBinding(inflater: LayoutInflater): FragmentFavouritesBinding {
        return FragmentFavouritesBinding.inflate(inflater)
    }

    override fun useViews() {
        binding.apply {
            courseList.adapter = coursesAdapter
        }
    }

    override fun useState() {
        viewModel.state.consume(viewLifecycleOwner){
            with(it){
                loading?.let { show ->
                    binding.loading.isVisible = show
                }
                courses?.let { list ->
                    coursesAdapter.setList(list.toMutableList())
                    binding.noSavedCourses.isVisible = coursesAdapter.itemCount == 0
                }
            }
        }
    }
    private fun gotoDetails(course: Course){
        findNavControllerById(R.id.main_navigation_fragment)
            .navigate(TabsFragmentDirections.actionTabsFragmentToCourseDetailFragment(course))
    }
}