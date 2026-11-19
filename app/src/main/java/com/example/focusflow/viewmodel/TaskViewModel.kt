package com.example.focusflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.focusflow.data.Task
import com.example.focusflow.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository,
    private val userId: String
) : ViewModel() {

    // Displays tasks belonging to the current user
    val tasks: StateFlow<List<Task>> =
        repository.getTasks(userId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Creates a new task
    fun addTask(
        title: String,
        description: String,
        dueDate: String,
        priority: String
    ) {
        if (userId.isBlank()) return

        viewModelScope.launch {
            repository.addTask(
                Task(
                    userId = userId,
                    title = title.trim(),
                    description = description.trim(),
                    dueDate = dueDate,
                    priority = priority
                )
            )
        }
    }

    // Updates an existing task
    fun updateTask(task: Task) {
        if (task.userId != userId || userId.isBlank()) return

        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    // Marks a task as completed or incomplete
    fun toggleComplete(task: Task) {
        if (task.userId != userId) return

        updateTask(
            task.copy(isCompleted = !task.isCompleted)
        )
    }

    // Deletes a task
    fun deleteTask(task: Task) {
        if (task.userId != userId || userId.isBlank()) return

        viewModelScope.launch {
            repository.deleteTask(task.id, userId)
        }
    }
}

// Creates the ViewModel with its repository and user ID
class TaskViewModelFactory(
    private val repository: TaskRepository,
    private val userId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository, userId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}