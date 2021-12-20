package com.cherrio.skool.presentation.pick_course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cherrio.skool.R
import com.cherrio.skool.databinding.ItemCategoryBinding
import com.cherrio.skool.databinding.ItemCourseBinding
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.utils.loadImage

/**
 *Created by Ayodele on 10/12/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class CategoryAdapter(private val categoryClicked: (Category, Int) -> Unit):
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var courseList = mutableListOf<Category>()
    private var pos = -1

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = courseList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(createCategoryBinding(parent))
    }


    inner class CategoryViewHolder(private val binding: ItemCategoryBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(category: Category){
            binding.apply {
                if (category.checked){
                    curriculumNo.isChecked = true
                    pos = absoluteAdapterPosition
                }else{
                    curriculumNo.isChecked = false
                }
                curriculumNo.text = category.cat

                root.setOnClickListener {
                    categoryClicked(category, absoluteAdapterPosition)
                }

            }
        }
    }
    fun notify(position: Int){
        courseList[position].checked = true
        courseList[pos].checked = false
        notifyDataSetChanged()
    }
    fun setList(list: MutableList<Category>){
        courseList = list
        notifyDataSetChanged()
    }
    fun getSelectedCategory(): Category = courseList.filter { it.checked }[0]

    companion object {
        fun createCategoryBinding(parent: ViewGroup): ItemCategoryBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemCategoryBinding.inflate(inflater, parent, false)
        }
    }

    override fun getItemCount() = courseList.size


}