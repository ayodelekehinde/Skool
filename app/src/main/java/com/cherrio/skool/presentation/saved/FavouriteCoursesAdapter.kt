package com.cherrio.skool.presentation.saved

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

class FavouriteCoursesAdapter(private val courseClicked: (Course) -> Unit):
    RecyclerView.Adapter<FavouriteCoursesAdapter.CourseViewHolder>() {

    private var courseList = mutableListOf<Course>()

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val item = courseList[position]
        holder.bind(item)
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
                    courseClicked(course)
                }

            }
        }
    }
    fun setList(list: MutableList<Course>){
        courseList = list
        notifyDataSetChanged()
    }

    companion object {
        fun createCourseBinding(parent: ViewGroup): ItemCourseBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemCourseBinding.inflate(inflater, parent, false)
        }
    }

    override fun getItemCount() = courseList.size


}