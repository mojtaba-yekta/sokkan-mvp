package com.negahpay.sokkan.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        SettingEntity::class
    ],
    version = 1
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun settingDao(): SettingDao

    companion object {
        private const val DATABASE_NAME = "sokkan.db"

        private var instance: DatabaseService? = null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }
}