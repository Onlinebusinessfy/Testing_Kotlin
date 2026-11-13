package com.samuel.todoch12.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuel.todoch12.Task
import com.samuel.todoch12.TodoViewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel
) {
    // State that holds what the user is currently entering
    // remember allows the state to survive configuration changes
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "My Todo List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Challenge 1: Add a TextField for this row
            //Enter a task
            TextField(
                value = inputText,
                onValueChange = { inputText = it},
                label = {Text("Enter a Task")},
                modifier = Modifier.weight(1f)
                    .testTag("inputText"),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            //Challenge 2: Add a Button to this row
            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        viewModel.addTask(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier.testTag("addButton")
            ) {
                Text("Add")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = "${viewModel.tasks.size} tasks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = viewModel.tasks,
                key = {task -> task.id}
            ){ task ->
                TaskItem(
                    task = task,
                    onDeleted = { viewModel.removeTask(task.id)}
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDeleted: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onDeleted
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Detele task",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoScreenPreview(){
    TodoScreen(viewModel = TodoViewModel())
}
@Preview(showBackground = true)
@Composable
fun TaskItemPreview(){
    TaskItem(task = Task(1, "Do exercise"), onDeleted = {})
}