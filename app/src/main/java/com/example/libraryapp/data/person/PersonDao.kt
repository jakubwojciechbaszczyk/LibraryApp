package com.example.libraryapp.data.person

import androidx.room.*
import com.example.libraryapp.data.person.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao{
    @Query("SELECT * FROM person_table ORDER BY person ASC")
    fun getPerson(): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(persons: List<Person>)

    @Delete
    suspend fun delete(person: Person)

    @Query("DELETE FROM position_table")
    suspend fun deleteAll()
}