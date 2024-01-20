package com.example.libraryapp.data.rent

import androidx.annotation.WorkerThread
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.data.rent.RentDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RentRepository @Inject constructor(
    private val rentDao: RentDao
) : RentDataSource {
    val rents: Flow<List<Rent>> = rentDao.getRents()
    @WorkerThread
    override suspend fun rents() {
        rentDao.getRents()
    }

    @WorkerThread
    override suspend fun insert(rent: Rent) {
        rentDao.insert(rent)
    }

    @WorkerThread
    override suspend fun edit(rent: Rent) {
        rentDao.update(rent)
    }
}