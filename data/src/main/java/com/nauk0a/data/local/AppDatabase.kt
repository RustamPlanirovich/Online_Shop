package com.nauk0a.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nauk0a.data.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}