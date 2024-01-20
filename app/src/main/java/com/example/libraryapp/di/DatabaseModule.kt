package com.example.libraryapp.di

import android.content.Context
import androidx.room.Room
import com.example.libraryapp.data.PositionRoomDatabase
import com.example.libraryapp.data.person.PersonDao
import com.example.libraryapp.data.position.PositionDao
import com.example.libraryapp.data.rent.RentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun providePositionDao(database: PositionRoomDatabase): PositionDao {
        return database.positionDao()
    }
    @Provides
    fun providePersonDao(database: PositionRoomDatabase): PersonDao {
        return database.personDao()
    }

    @Provides
    fun provideRentDao(database: PositionRoomDatabase): RentDao {
        return database.rentDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PositionRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            PositionRoomDatabase::class.java,
            "positioning.dp"
        ).build()
    }
}