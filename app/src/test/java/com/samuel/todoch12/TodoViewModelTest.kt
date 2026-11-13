package com.samuel.todoch12

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class TodoViewModelTest {
    // The system under test - recreated fresh before each state
    private lateinit var _viewModel: TodoViewModel
    @Before
    fun setUp(){
        _viewModel = TodoViewModel()
    }

    @Test
    fun addTask_withValidTitle_appearsInList(){
        // Arrange- the setUp() already created an empty viewModel

        // Act - call the function being tested
        _viewModel.addTask("Buy Groceries")

        // Assert - check teh result
        assertEquals(1, _viewModel.getTaskCount())
        assertTrue(_viewModel.containsTask("Buy Groceries"))
    }

    @Test
    fun addTask_withBlankTitle_isIgnore(){
        _viewModel.addTask("   ")
        assertEquals(0, _viewModel.getTaskCount())
    }

    // Challenge test with empty string
    @Test
    fun addTask_withEmptySpace_isIgnore(){
        _viewModel.addTask("")
        assertEquals(0, _viewModel.getTaskCount())
    }

    // Challenge test the Trimmed String
    @Test
    fun addTask_withTrimmedTitle_appearsInList(){
        _viewModel.addTask(" Buy Groceries ")
        assertTrue(_viewModel.containsTask("Buy Groceries"))
    }

    @Test
    fun removeTask_removesTaskFromList(){
        // Arrange - add a task first so we can remove it
        _viewModel.addTask("Buy Groceries")
        val taskId = _viewModel.tasks[0].id
        // Act - call the function being tested
        _viewModel.removeTask(taskId)
        // Assert - check the result
        assertEquals(0, _viewModel.getTaskCount())
        assertFalse(_viewModel.containsTask("Buy Groceries"))
    }

    @Test
    fun removeTask_removesCorrectTask_whenMultipleExists(){
        // Arrange - add multiple tasks so we can remove one of them
        _viewModel.addTask("Task A")
        _viewModel.addTask("Task B")
        _viewModel.addTask("Task C")
        val idOfB = _viewModel.tasks[1].id

        // Act - call the function being tested
        _viewModel.removeTask(idOfB)

        // Assert - check the result
        assertEquals(2, _viewModel.getTaskCount())
        assertTrue(_viewModel.containsTask("Task A"))
        assertFalse(_viewModel.containsTask("Task B"))
        assertTrue((_viewModel.containsTask("Task C")))
    }
}