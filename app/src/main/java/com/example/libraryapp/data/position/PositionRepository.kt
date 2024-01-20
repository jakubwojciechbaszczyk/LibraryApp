package com.example.libraryapp.data.position

import androidx.annotation.WorkerThread
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.data.position.PositionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PositionRepository @Inject constructor(
    private val positionDao: PositionDao
    ) : PositionDataSource {
    val allPositions: Flow<List<Position>> = positionDao.getPositions()
    val allFreePositions: Flow<List<Position>> = positionDao.getFreePositions()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(position: Position) {
        positionDao.insert(position)
    }

    @WorkerThread
    override suspend fun delete(position: Position) {
        positionDao.delete(position)
    }

    @WorkerThread
    override suspend fun edit(position: Position) {
        positionDao.edit(position)
    }

    @WorkerThread
    override suspend fun allPositions(positions: List<Position>) {
        positionDao.getPositions()
    }

    @WorkerThread
    override suspend fun allFreePositions(positions: List<Position>) {
        positionDao.getFreePositions()
    }
}