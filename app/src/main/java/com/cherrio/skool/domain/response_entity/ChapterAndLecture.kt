package com.cherrio.skool.domain.response_entity

/**
 *Created by Ayodele on 12/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class CurriculumPage(val next: String?,
                      val results: List<ChapterAndLecture>)
sealed class ChapterAndLecture(val id: Long){
    data class Chapter(val chapterId: Long,
                       val chapter: String): ChapterAndLecture(chapterId)
    data class Lecture(
        val lectureId: Long,
        val title: String,
        val type: String
    ): ChapterAndLecture(lectureId)
}
