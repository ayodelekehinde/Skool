package com.cherrio.skool.domain.home

import com.cherrio.skool.domain.request_entity.RequestCourses
import com.cherrio.skool.domain.response_entity.ChapterAndLecture
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.CoursePage
import com.cherrio.skool.domain.response_entity.CurriculumPage
import com.cherrio.skool.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class GetCurriculumUseCase @Inject constructor(private val homeRepository: UdemyRepository) {

    suspend operator fun invoke(page: Int, id: Long): Resource<CurriculumPage>{
        return try {
            //Get the filters from saved
                val response = homeRepository.getCurriculum(page.toString(), id)
            val filteredList = response.results.filter { it.type == "chapter" || it.type == "lecture" }
            val chapterAndLecture = filteredList.map {
                if (it.type == "chapter") ChapterAndLecture.Chapter(it.courseId, it.title)
                else ChapterAndLecture.Lecture(it.courseId, it.title, it.assetDTO!!.assetType)
            }
            Resource.Success(CurriculumPage(response.next, chapterAndLecture))
        }catch (e: HttpException){
            Resource.Failed<CurriculumPage>(e.localizedMessage ?: "Error occurred")
        }catch (e: IOException){
            Resource.Failed<CurriculumPage>(e.localizedMessage ?: "Network Error")
        }
    }
}