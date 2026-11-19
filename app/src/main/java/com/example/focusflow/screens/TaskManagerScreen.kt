package com.example.focusflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.focusflow.data.Task
import com.example.focusflow.viewmodel.TaskViewModel

// colors
private val NavyBlue = Color(0xFF142B52)
private val RoyalBlue = Color(0xFF356AE6)
private val LightBlue = Color(0xFFEEF3FF)
private val TextGray = Color(0xFF64748B)
private val BackgroundColor = Color(0xFFF6F8FC)

@Composable
fun TaskManagerScreen(
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {

    // Observe task list
    val tasks by taskViewModel.tasks.collectAsStateWithLifecycle()

    // Dialog states
    var showDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    // Main screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp, vertical = 44.dp)
    ) {

        // Screen heading
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold, color = NavyBlue
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Organise your assignments and study tasks.",
            style = MaterialTheme.typography.bodyMedium, color = TextGray
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Add task button
        Button(
            onClick = {
                editingTask = null
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RoyalBlue
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            )
        ) {
            Text(
                text = "+  Add New Task", fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium, color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Task section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Tasks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, color = NavyBlue
            )

            // Task count
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = LightBlue
            ) {
                Text(
                    text = "${tasks.size} Tasks",
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    ),
                    color = RoyalBlue, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Task list
        if (tasks.isEmpty()) {

            // Empty task message
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Tasks Yet", style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold, color = NavyBlue
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Add your first task to get started!",
                        style = MaterialTheme.typography.bodyMedium, color = TextGray
                    )
                }
            }

        } else {

            // Display tasks
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->

                    TaskItem(
                        task = task,
                        onComplete = {
                            taskViewModel.toggleComplete(task)
                        },
                        onEdit = {
                            editingTask = task
                            showDialog = true
                        },
                        onDelete = {
                            taskViewModel.deleteTask(task)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = NavyBlue
            )
        ) {
            Text(
                text = "Back to Dashboard",
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    // Task form dialog
    if (showDialog) {

        TaskFormDialog(
            task = editingTask,

            onDismiss = {
                showDialog = false
                editingTask = null
            },

            // Save task changes
            onSave = { title, description, dueDate, priority ->

                if (editingTask == null) {

                    taskViewModel.addTask(
                        title,
                        description,
                        dueDate,
                        priority
                    )

                } else {

                    taskViewModel.updateTask(
                        editingTask!!.copy(
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            priority = priority
                        )
                    )
                }
                showDialog = false
                editingTask = null
            }
        )
    }
}

// Individual task item
@Composable
fun TaskItem(
    task: Task,
    onComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    // Priority colors
    val priorityColor = when (task.priority.lowercase()) {
        "high" -> Color(0xFFDC4545)
        "medium" -> Color(0xFFE49A27)
        "low" -> Color(0xFF269B70)
        else -> TextGray
    }

    // Task card
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Title and status
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        onComplete()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = RoyalBlue
                    )
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.title, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = if (task.isCompleted) TextGray else NavyBlue
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = if (task.isCompleted) "Completed" else "Pending",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (task.isCompleted) {
                            Color(0xFF269B70)
                        } else {
                            RoyalBlue
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Priority badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = priorityColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = task.priority,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        color = priorityColor, style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                color = Color(0xFFEDF0F5)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Task details
            Text(
                text = task.description, style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Task deadline
            Text(
                text = "Deadline: ${task.dueDate}", style = MaterialTheme.typography.bodySmall,
                color = TextGray, fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Task actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onEdit
                ) {
                    Text(
                        text = "Edit", color = RoyalBlue, fontWeight = FontWeight.SemiBold
                    )
                }

                TextButton(
                    onClick = onDelete
                ) {
                    Text(
                        text = "Delete", color = Color(0xFFDC4545), fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Task form dialog
@Composable
fun TaskFormDialog(
    task: Task?,
    onDismiss: () -> Unit,
    onSave: (
        String,
        String,
        String,
        String
    ) -> Unit
) {

    // Form values
    var title by remember(task) {
        mutableStateOf(task?.title ?: "")
    }
    var description by remember(task) {
        mutableStateOf(task?.description ?: "")
    }
    var dueDate by remember(task) {
        mutableStateOf(task?.dueDate ?: "")
    }
    var priority by remember(task) {
        mutableStateOf(task?.priority ?: "Medium")
    }
    var errorMessage by remember {
        mutableStateOf("")
    }

    // Main dialog
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,

        // Dialog title
        title = {
            Text(
                text = if (task == null) {
                    "Create New Task"
                } else {
                    "Edit Task"
                },
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = NavyBlue
            )
        },

        // Form fields
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Task title
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Task Title")
                    },
                    placeholder = {
                        Text("Enter task title")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Task details
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Description")
                    },
                    placeholder = {
                        Text("Enter task details")
                    },
                    minLines = 2,
                    maxLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )

                // Task deadline
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = {
                        dueDate = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Deadline")
                    },
                    placeholder = {
                        Text("YYYY-MM-DD")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Priority label
                Text(
                    text = "Select Priority", style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold, color = NavyBlue
                )

                // Priority options
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("High", "Medium", "Low").forEach { priorityOption ->

                        FilterChip(
                            selected = priority == priorityOption,
                            onClick = {
                                priority = priorityOption
                            },
                            label = {
                                Text(priorityOption)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = LightBlue,
                                selectedLabelColor = RoyalBlue
                            )
                        )
                    }
                }

                // Validation error
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },

        // Save task
        confirmButton = {
            Button(
                onClick = {
                    when {
                        title.isBlank() -> {
                            errorMessage = "Please enter a task title."
                        }

                        !dueDate.matches(
                            Regex("\\d{4}-\\d{2}-\\d{2}")
                        ) -> {
                            errorMessage =
                                "Enter the deadline as YYYY-MM-DD."
                        }

                        else -> {
                            onSave(
                                title.trim(),
                                description.trim(),
                                dueDate,
                                priority
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RoyalBlue
                )
            ) {
                Text(
                    text = "Save", fontWeight = FontWeight.Bold, color = Color.White
                )
            }
        },

        // Cancel dialog
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel", color = TextGray
                )
            }
        }
    )
}