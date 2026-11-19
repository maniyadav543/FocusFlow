package com.example.focusflow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database configuration
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {

    // Provides task DAO
    abstract fun taskDao(): TaskDao

    companion object {

        // Database instance
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Creates database
        fun getDatabase(context: Context): TaskDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "focusflow_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}