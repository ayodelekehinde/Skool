package com.cherrio.skool.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cherrio.skool.R
import com.cherrio.skool.databinding.ItemCurriculumBinding
import com.cherrio.skool.databinding.ItemCurriculumChapterBinding
import com.cherrio.skool.domain.response_entity.ChapterAndLecture
import com.cherrio.skool.domain.response_entity.Course

/**
 *Created by Ayodele on 10/12/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class CurriculumAdapter:
    PagingDataAdapter<ChapterAndLecture, RecyclerView.ViewHolder>(DIFF) {

    private var lecturePosition = 0

    object DIFF : DiffUtil.ItemCallback<ChapterAndLecture>() {
        override fun areItemsTheSame(oldItem: ChapterAndLecture, newItem: ChapterAndLecture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChapterAndLecture, newItem: ChapterAndLecture): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is ChapterViewHolder ->{
                holder.bind(item!! as ChapterAndLecture.Chapter)
                lecturePosition = 0
            }
            is LectureViewHolder ->{
                holder.bind(item!! as ChapterAndLecture.Lecture)
                lecturePosition++
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            CHAPTER -> ChapterViewHolder(createChapterBinding(parent))
            else -> LectureViewHolder(createLectureBinding(parent))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is ChapterAndLecture.Chapter) CHAPTER else LECTURE
    }


    inner class LectureViewHolder(private val binding: ItemCurriculumBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(lecture: ChapterAndLecture.Lecture){
            binding.apply {
                lectureType.text = lecture.type
                lectureTitle.text = lecture.title
                lectureNo.text = root.context.resources.getString(R.string.txt_lecture).plus(" ${lecturePosition + 1}")
            }
        }
    }
    inner class ChapterViewHolder(private val binding: ItemCurriculumChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chapter: ChapterAndLecture.Chapter) {
            binding.apply {
                chapterTitle.text = chapter.chapter
            }
        }
    }

    companion object {
        const val CHAPTER = 0
        const val LECTURE = 1

        fun createChapterBinding(parent: ViewGroup): ItemCurriculumChapterBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemCurriculumChapterBinding.inflate(inflater, parent, false)
        }
        fun createLectureBinding(parent: ViewGroup): ItemCurriculumBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemCurriculumBinding.inflate(inflater, parent, false)
        }
    }


}