package com.samuel.todoch12

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel : ViewModel() {
    companion object{
        private const val TAG = "TodoViewModel"
    }
    // _task is private -- only the ViewModel can modify it
    // This is a compose-aware mutable list that holds all current tasks
    private val _tasks = mutableStateListOf<Task>()
    // task is public -- the UI reads from the list but cannot modify it
    val tasks : List<Task> get() = _tasks

    // counter used for generate a unique id for each task
    private var nextId = 1

//     Adds a new task to the list of tasks
//     Does nothing if the title is blank
    fun addTask(title: String){
        if (title.isBlank()) {
            // Log.w(TAG, "addTask() called with blank title- task was ignored")
            return
        }

        _tasks.add(Task(id = nextId++, title = title.trim()))
        // Log.d(TAG, "Task added: '${title.trim()}' --total task : ${_tasks.size}")
    }

    // Removes a task from a list of tasks by its id
    fun removeTask(taskId : Int){
        val taskToRemove = _tasks.find {it.id == taskId}
        if (taskToRemove != null){
            _tasks.removeAll { it.id == taskId }
            // Log.d(TAG, "Task removed: id = $taskId title = '${taskToRemove.title}'")
        }
        else {
            // Log.w(TAG, "removeTask() called with id=$taskId - task not found")
        }
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