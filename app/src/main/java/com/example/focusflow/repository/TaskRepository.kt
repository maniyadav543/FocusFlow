package com.example.focusflow.repository

import com.example.focusflow.data.Task
import com.example.focusflow.data.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {

    // Fetches user tasks
    fun getTasks(userId: String): Flow<List<Task>> {
        return taskDao.getAllTasks(userId)
    }

    // Adds new task
    suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    // Updates task details
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    // Removes selected task
    suspend fun deleteTask(taskId: Int, userId: String) {
        taskDao.deleteTask(taskId, userId)
    }
}