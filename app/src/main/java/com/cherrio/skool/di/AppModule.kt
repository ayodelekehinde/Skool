package com.cherrio.skool.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cherrio.skool.data.local.CourseDAO
import com.cherrio.skool.data.local.DataStoreManager
import com.cherrio.skool.data.local.SkoolDatabase
import com.cherrio.skool.data.remote.UdemyApi
import com.cherrio.skool.data.repo.LocalRepoImpl
import com.cherrio.skool.data.repo.UdemyRepoImpl
import com.cherrio.skool.domain.fav.LocalRepository
import com.cherrio.skool.domain.home.UdemyRepository
import com.cherrio.skool.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideLocalRepo(udemyApi: UdemyApi):UdemyRepository{
        return UdemyRepoImpl(udemyApi)
    }
    @Provides
    @Singleton
    fun provideHomeRepo(courseDAO: CourseDAO, dataStoreManager: DataStoreManager): LocalRepository{
        return LocalRepoImpl(courseDAO, dataStoreManager)
    }



    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient())
            .build()

    @Provides
    @Singleton
    fun provideUdemyApi(retrofit: Retrofit): UdemyApi =
        retrofit.create(UdemyApi::class.java)

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(provideLogging())
            .build()

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideCourseDao(@ApplicationContext app: Context): CourseDAO =
        Room.databaseBuilder(app, SkoolDatabase::class.java, "skool_database").build().getCourseDao()

}