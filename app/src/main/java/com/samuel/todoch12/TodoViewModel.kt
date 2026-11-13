package com.samuel.todoch12

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    // _task is private -- only the ViewModel can modify it
    // This is a compose-aware mutable list that holds all current tasks
    private val _tasks = mutableStateListOf<Task>()
    // task is public -- the UI reads from the list but cannot modify it
    val tasks : List<Task> get() = _tasks

    // counter used for generate a unique id for each task
    private var nextId = 1

    // Adds a new task to the list of tasks
    // Does nothing if the title is blank
    fun addTask(title: String){
        if (title.isBlank()) return
        _tasks.add(Task(id = nextId++, title = title.trim()))
    }

    // Removes a task from a list of tasks by its id
    fun removeTask(taskId : Int){
        _tasks.removeAll { it.id == taskId }
    }

    // Returns the number of tasks in the list
    // Used in unit test to verify the number of tasks in the list
    fun getTaskCount(): Int {
        return _tasks.size
    }

    fun containsTask(title: String): Boolean{
        return _tasks.any{it.title == title}
    }
}