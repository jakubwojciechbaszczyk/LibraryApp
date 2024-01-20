package com.example.libraryapp.data.position

interface PositionDataSource {
    suspend fun insert(position: Position)
    suspend fun delete(position: Position)
    suspend fun edit(position: Position)
    suspend fun allPositions(positions: List<Position>)
    suspend fun allFreePositions(positions: List<Position>)
}