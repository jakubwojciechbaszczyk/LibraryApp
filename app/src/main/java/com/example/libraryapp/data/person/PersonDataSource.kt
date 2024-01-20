package com.example.libraryapp.data.person

import kotlinx.coroutines.flow.Flow

interface PersonDataSource {
    suspend fun insert(person: List<Person>)
    fun allPerson(): Flow<List<Person>>
}