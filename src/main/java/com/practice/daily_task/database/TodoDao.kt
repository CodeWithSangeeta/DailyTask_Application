package com.practice.daily_task.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow




@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo")
    fun getAllTodo() : Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo : Todo)
   //suspend  -> Must be called inside a coroutine (because database operations should not run on the main thread)

    @Query("DELETE FROM Todo WHERE id = :id")
    suspend fun deleteTodo(id : Int)

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getTodoById(id : Int) : Flow<Todo?>

    @Update
    suspend fun updateTodo(todo : Todo)

}