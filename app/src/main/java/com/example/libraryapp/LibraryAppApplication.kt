package com.example.libraryapp

import android.app.Application
import com.example.libraryapp.data.person.PersonRepository
import com.example.libraryapp.data.position.PositionRepository
import com.example.libraryapp.data.PositionRoomDatabase
import com.example.libraryapp.data.rent.RentRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class LibraryAppApplication : Application()// {
//
//    private val applicationScope = CoroutineScope(SupervisorJob())
//
//    // Using by lazy so the database and the repository are only created when they're needed
//    // rather than when the application starts
//    val database by lazy { PositionRoomDatabase.getDatabase(this, applicationScope) }
//    val rentalRepository by lazy { RentRepository(database.rentDao()) }
//    val positionRepository by lazy { PositionRepository(database.positionDao()) }
//    val personRepository by lazy { PersonRepository(database.personDao()) }
//}