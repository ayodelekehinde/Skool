package com.cherrio.skool.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@Database(entities = [CourseEntity::class], version = 1)
abstract class SkoolDatabase: RoomDatabase() {
    abstract fun getCourseDao(): CourseDAO
}