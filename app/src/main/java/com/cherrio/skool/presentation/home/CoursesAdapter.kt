package com.cherrio.skool.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cherrio.skool.R
import com.cherrio.skool.databinding.ItemCourseBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.utils.loadImage

/**
 *Created by Ayodele on 10/12/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class CoursesAdapter(private val requestClicked: (Course) -> Unit):
    PagingDataAdapter<Course, CoursesAdapter.CourseViewHolder>(DIFF) {



    object DIFF : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.courseId == newItem.courseId
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(createCourseBinding(parent))
    }


    inner class CourseViewHolder(private val binding: ItemCourseBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(course: Course){
            binding.apply {
                courseImage.loadImage(loading,course.image)
               courseHeadline.text = course.headline
                courseTitle.text = course.title
                val color = when (course.price) {
                    "Free" -> {
                        ContextCompat.getColor(
                            root.context,
                            R.color.purple_200
                        )
                    }
                    else -> {
                        ContextCompat.getColor(
                            root.context,
                            R.color.teal_700
                        )
                    }
                }
                coursePrice.setTextColor(color)
                coursePrice.text = course.price

                root.setOnClickListener {
                    requestClicked(course)
                }

            }
        }
    }

    companion object {
        fun createCourseBinding(parent: ViewGroup): ItemCourseBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemCourseBinding.inflate(inflater, parent, false)
        }
    }


}