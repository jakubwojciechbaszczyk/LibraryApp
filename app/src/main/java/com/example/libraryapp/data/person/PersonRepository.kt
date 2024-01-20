package com.example.libraryapp.data.person

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.person.PersonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personDao: PersonDao
    ) : PersonDataSource {
    val allPerson: Flow<List<Person>> = personDao.getPerson()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(persons: List<Person>) {
        personDao.insert(persons)
    }

    @WorkerThread
    override fun allPerson(): Flow<List<Person>> {
        return personDao.getPerson()
    }
}