package com.example.libraryapp.data.rent

import androidx.room.*
import com.example.libraryapp.data.rent.Rent
import kotlinx.coroutines.flow.Flow

@Dao
interface RentDao {
    @Query("SELECT * FROM rent_table ORDER BY rental_id ASC")
    fun getRents(): Flow<List<Rent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rent: Rent)

    @Update
    suspend fun update(rent: Rent)

    @Delete
    suspend fun delete(rent: Rent)

    @Query("DELETE FROM rent_table")
    suspend fun deleteAll()
}