package com.cherrio.skool.presentation.details

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.cherrio.skool.R
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentCourseDetailBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.presentation.FooterAdapter
import com.cherrio.skool.utils.consume
import com.cherrio.skool.utils.loadImage
import com.cherrio.skool.utils.scrollingUpOrDown
import com.cherrio.skool.utils.snack
import dagger.hilt.android.AndroidEntryPoint

/**
 *Created by Ayodele on 12/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@AndroidEntryPoint
class CourseDetailFragment: BaseFragment<FragmentCourseDetailBinding>() {
    private val args: CourseDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<CourseDetailViewModel>()
    private val curriculumAdapter = CurriculumAdapter()
    private var fav = false

    override fun getBinding(inflater: LayoutInflater): FragmentCourseDetailBinding {
        return FragmentCourseDetailBinding.inflate(inflater)
    }

    override fun useViews() {
        val course = args.course
         fav = course.isFav
        binding.apply {
            curriculumList.adapter = curriculumAdapter.withLoadStateFooter(
                FooterAdapter(curriculumAdapter::retry)
            )
            courseImage.loadImage(course.image)
            courseHeadline.text = course.headline
            courseTitle.text = course.title
            val res = if (course.isFav) R.drawable.ic_round_favorite else R.drawable.ic_round_favorite_border
            btnFav.setImageResource(res)
            instructorImage.loadImage(course.instructorImage)
            instructorName.text = course.instructor
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnFav.setOnClickListener {
               handleFaveButton(course)
            }
            scroll.scrollingUpOrDown {
                if (it) appBar.elevation = 20f
                else appBar.elevation = 0f
            }
        }
        viewModel.getPagedData(course.courseId).consume(viewLifecycleOwner){
            curriculumAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        curriculumAdapter.addLoadStateListener { loadstate ->
            handleState(loadstate.source)
        }

    }
    private fun handleFaveButton(course: Course){
        var message = ""
        var drawable = R.drawable.ic_round_favorite
        if (fav) {
            drawable = R.drawable.ic_round_favorite_border
            viewModel.addOrRemoveCourse(false, course)
            message = "Removed from favourites successfully"
            fav = false
        }else{
            viewModel.addOrRemoveCourse(true, course)
            message = "Added to favourites successfully"
            fav = true
        }
        snack(binding.root, message)
        binding.btnFav.setImageResource(drawable)
    }

    override fun useState() {}

    private fun handleState(states: LoadStates) {
        when (states.refresh) {
            is LoadState.Loading -> binding.progressBar.isVisible = true
            is LoadState.Error -> {
                binding.progressBar.isVisible = false
            }
            is LoadState.NotLoading -> {
                binding.progressBar.isVisible = false
            }
        }
    }
}