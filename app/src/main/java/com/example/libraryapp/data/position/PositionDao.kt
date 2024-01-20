package com.example.libraryapp.data.position

import androidx.room.*
import com.example.libraryapp.data.position.Position
import kotlinx.coroutines.flow.Flow

@Dao
interface PositionDao {
    @Query("Select * FROM position_table ORDER BY position ASC")
    fun getPositions(): Flow<List<Position>>

    @Query("Select * FROM position_table WHERE isRented = false ORDER BY position ASC")
    fun getFreePositions(): Flow<List<Position>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(position: Position)

    @Delete
    suspend fun delete(position: Position)

    @Update
    suspend fun edit(position: Position)

    @Query("DELETE FROM position_table")
    suspend fun deleteAll()
}