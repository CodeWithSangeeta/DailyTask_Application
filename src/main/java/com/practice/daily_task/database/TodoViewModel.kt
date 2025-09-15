package com.practice.daily_task.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.daily_task.todoUI.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoDao: TodoDao,
                                         private val UserProfileDao: UserProfileDao) : ViewModel() {
    val todoList: StateFlow<List<Todo>> = todoDao.getAllTodo()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isDataReady: StateFlow<Boolean> = todoList
        .map { it.isNotEmpty() || true }   // if you want to hide splash even when empty
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)


    fun addTodo(
        title: String,
        description: String,
        dueDate: Date? = null,
        selectedPriority: Priority,
        isReminderSet: Boolean,
        reminderTime: Long? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(
                    title = title,
                    description = description,
                    createdAt = Date.from(Instant.now()),
                    dueDate = dueDate,
                    priority = selectedPriority,
                    isReminderSet = isReminderSet,
                    reminderTime = reminderTime
                )
            )
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }

    fun updateTodo(
        id: Int,
        newTitle: String,
        newDescription: String,
        newDueDate: Date?,
        selectedPriority: Priority,
        isReminderSet: Boolean,
        reminderTime: Long? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {// It's good practice to use Dispatchers.IO for database operations
            val todoToUpdate =
                todoDao.getTodoById(id).firstOrNull() // Assuming getTodoById returns a Flow
            todoToUpdate?.let {
                val updatedTodo = it.copy(
                    title = newTitle,
                    description = newDescription,
                    dueDate = newDueDate,
                    priority = selectedPriority,
                    isReminderSet = isReminderSet,
                    reminderTime = reminderTime
                )
                todoDao.updateTodo(updatedTodo)
            }
        }
    }

    fun getTodoById(id: Int): Flow<Todo?> {
        return todoDao.getTodoById(id)
    }


    //user data
    val userData: StateFlow<userProfile?> = UserProfileDao.getUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveUserProfile(
        name: String,
        email: String,
        phone: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userProfile(id=1,name = name, email = email, phone = phone)
            UserProfileDao.saveUser(user)
        }
    }

    fun deleteUserProfile(profile : userProfile) {
        viewModelScope.launch (Dispatchers.IO){
            UserProfileDao.deletetUser(profile)
        }
    }

    fun markTodo (id:Int,dueDate : Date?){
        viewModelScope.launch(Dispatchers.IO){
            if((dueDate == null) || (dueDate !=null && Date().before(dueDate))){
                todoDao.updateMarkStatus(id,true)
            }
        }
    }

}




