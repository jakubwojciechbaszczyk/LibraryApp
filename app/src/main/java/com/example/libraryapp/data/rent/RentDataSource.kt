package com.example.libraryapp.data.rent

interface RentDataSource {
    suspend fun rents()
    suspend fun insert(rent: Rent)
    suspend fun edit(rent: Rent)
}