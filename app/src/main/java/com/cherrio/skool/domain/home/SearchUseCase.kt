package com.cherrio.skool.domain.home

import com.cherrio.skool.data.remote.model.UdemyCourse
import com.cherrio.skool.domain.fav.LocalRepository
import com.cherrio.skool.domain.request_entity.RequestCourses
import com.cherrio.skool.domain.request_entity.RequestSearch
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.CoursePage
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class SearchUseCase @Inject constructor(private val homeRepository: UdemyRepository,
                                        private val localRepository: LocalRepository
) {

    suspend operator fun invoke(page: Int, query: String): Resource<CoursePage>{
        return try {
            //Get the filters from saved
                val requestSearch = RequestSearch(
                   page.toString(),
                   query)

               val courseDtos = homeRepository.search(requestSearch)
               val courses = courseDtos.results.map {
                   val teacher = it.instructors[0]
                   val isFav = localRepository.getCourse(it.courseId).isNotEmpty()
                   Course(it.courseId,it.title,it.link,it.price,it.image,it.headline,teacher.title,teacher.image,isFav)
               }
            Resource.Success(CoursePage(courseDtos.next, courses))

        }catch (e: HttpException){
            Resource.Failed<CoursePage>(e.localizedMessage ?: "Error occurred")
        }catch (e: IOException){
            Resource.Failed<CoursePage>(e.localizedMessage ?: "Network Error")
        }
    }
}