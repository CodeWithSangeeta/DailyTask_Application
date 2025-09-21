package com.practice.daily_task.database

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.daily_task.todoUI.Priority
import com.practice.daily_task.todoUI.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun insertTodo(todo:Todo){
        viewModelScope.launch(Dispatchers.IO){
            todoDao.addTodo(todo)
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
        reminderTime: Long? = null,
        isMarked : Boolean
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
                    reminderTime = reminderTime,
                    isMarked = isMarked
                )
                todoDao.updateTodo(updatedTodo)
            }
        }
    }

    fun getTodoById(id: Int): Flow<Todo?> {
        return todoDao.getTodoById(id)
    }


    //user data
    val userData: StateFlow<Profile?> = UserProfileDao.getUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveUserProfile(
        name: String,
        email: String,
        phone: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = Profile(id=1,name = name, email = email, phone = phone)
            UserProfileDao.saveUser(user)
        }
    }

    fun clearUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val empty = Profile(
                id = 1,
                name = "",
                email = "",
                phone = "",
                profilePicPath = null
            )
            UserProfileDao.saveUser(empty)
        }
    }


    fun markTodo (id:Int,dueDate : Date?){
        viewModelScope.launch(Dispatchers.IO){
            if((dueDate == null) || (dueDate !=null && Date().before(dueDate))){
                todoDao.updateMarkStatus(id,true)
            }
        }
    }

    val user : Flow<Profile?> = UserProfileDao.getUser()

    //Save picked uri string to DB on IO dispatchers
    fun saveProfileUri(uri : Uri?){
        if(uri==null) return
        viewModelScope.launch(Dispatchers.IO){
            val current = user.firstOrNull()
            val update = if(current!=null){
                current.copy(profilePicPath= uri.toString())
            }
            else{
                Profile(
                    name = "User",
                    email = "user@gmail.com",
                    phone = "0000000000",
                    profilePicPath = uri.toString()
                )
            }
            UserProfileDao.saveUser(update)
        }
    }




    //Searching logic
    val allTasks : Flow<List<Todo>> = todoDao.getAllTodo()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.CREATED_FIRST)
    val sortOption = _sortOption.asStateFlow()

    //
    val filteredTasks : Flow<List<Todo>> = combine(allTasks, searchQuery,sortOption) {
       tasks, query ,sortOpt ->
       var filtered = if (query.isBlank()) {
            tasks
        }
        else {
           tasks.filter { tasks ->
               tasks.title.contains(query, ignoreCase = true) ||
                       tasks.description.contains(query, ignoreCase = true)
           }
       }


        filtered = when (sortOpt){
            SortOption.CREATED_FIRST -> filtered.sortedByDescending  { it.createdAt }
            SortOption.CREATED_LAST -> filtered.sortedBy{it.createdAt}
            SortOption.DUE_EARLIEST_FIRST -> filtered.sortedBy { it.dueDate }
            SortOption.DUE_LATEST_FIRST -> filtered.sortedByDescending {it.dueDate}
            SortOption.PRIORITY_HIGH_LOW-> filtered.sortedBy {it.priority}
            SortOption.PRIORITY_LOW_HIGH-> filtered.sortedByDescending {it.priority}
            SortOption.COMPLETE_FIRST -> filtered.sortedByDescending{it.isMarked}
            SortOption.INCOMPLETE_FIRST -> filtered.sortedBy {it.isMarked}
            SortOption.A_TO_Z -> filtered.sortedBy { it.title.lowercase() }
            SortOption.Z_TO_A -> filtered.sortedByDescending { it.title.lowercase() }
            else -> filtered
        }
        filtered
    }

    fun updateSearchQuery(newQuery:String){
        _searchQuery.value = newQuery
    }

    fun updateSortOption(newSort: SortOption){
        _sortOption.value = newSort
    }


    fun insertTodoAndReturnId(todo : Todo, onResult : (Int) -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            val rowId = todoDao.insertTodo(todo)
            withContext(Dispatchers.Main){
                onResult(rowId.toInt())
            }
        }
    }
}




