package com.example.libraryapp.data.person

import kotlinx.coroutines.flow.Flow

interface PersonDataSource {
    suspend fun insert(persons: List<Person>)
    fun allPerson(): Flow<List<Person>>
}