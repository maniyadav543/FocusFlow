package com.example.focusflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Stores the task owner's ID
    val userId: String,

    val title: String,
    val description: String,
    val dueDate: String,
    val priority: String,

    // Stores task completion status
    val isCompleted: Boolean = false
)