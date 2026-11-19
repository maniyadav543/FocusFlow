package com.example.focusflow.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Gets tasks for a specific user
    @Query("""
        SELECT * FROM tasks
        WHERE userId = :userId
        ORDER BY dueDate ASC
    """)
    fun getAllTasks(userId: String): Flow<List<Task>>

    // Adds a new task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    // Saves task changes
    @Update
    suspend fun updateTask(task: Task)

    // Deletes the user's selected task
    @Query("""
        DELETE FROM tasks
        WHERE id = :taskId AND userId = :userId
    """)
    suspend fun deleteTask(taskId: Int, userId: String)
}