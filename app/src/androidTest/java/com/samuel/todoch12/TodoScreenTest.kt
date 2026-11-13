package com.samuel.todoch12

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.samuel.todoch12.ui.screens.TodoScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoScreenTest {

    // This rule launches the composable in a test environment
    @get:Rule
    val composeTestRule = createComposeRule()

    // A fresh instance of the TodoScreen is created before each test
    private lateinit var _viewModel: TodoViewModel

    @Before
    fun setUp(){
        _viewModel = TodoViewModel()
        // set the content to display -- same as setContent{} from main activity
        composeTestRule.setContent {
            TodoScreen(viewModel = _viewModel)
        }
    }

    @Test
    fun addTask_userTypesAndClicksAdd_taskAppearsInList(){
        // Arrange - the setup() already created a fresh TodoScreen
        // verify the task does not exists yet
        composeTestRule
            .onNodeWithText("Buy Groceries")
            .assertDoesNotExist()

        // Act -- find the input text field by its test tag and enter text
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")

        // Act -- find the add button by its test tag and click it
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()

        // Assert -- verify the task appears in the list
        // Challenge test the task is added
        composeTestRule
            .onNodeWithText("Buy Groceries")
            .assertExists()
    }

    @Test
    fun addTask_withBlankInput_taskDoesNotAppear(){
        // Typing only spaces should not add a task
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("   ")
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()
        // The task counter should not have changed and still be 0
        composeTestRule
            .onNodeWithText("0 tasks")
            .assertIsDisplayed()
    }

    @Test
    fun addTask_InputFieldClear_afterAdding(){
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")
        // The input field should be empty after adding a task
        composeTestRule
            .onNodeWithTag("inputText")
            .assert(hasText("Enter a Task"))
    }

    // Challenge taskCounter updates when a task is added
    @Test
    fun addTask_updateTaskCounter_afterAddingTask(){
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()
        composeTestRule
            .onNodeWithText("1 tasks")
            .assertIsDisplayed()
    }
}