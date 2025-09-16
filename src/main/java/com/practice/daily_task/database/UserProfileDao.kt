package com.practice.daily_task.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user : Profile)

    @Query("SELECT * FROM Profile WHERE id = 1")
     fun getUser() : Flow<Profile?>

    @Delete
    suspend fun deletetUser(user : Profile)


}