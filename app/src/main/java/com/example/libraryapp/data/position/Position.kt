package com.example.libraryapp.data.position

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "position_table")
@Parcelize
data class Position(

    @ColumnInfo(name = "position")
    val identity: String,
    val title: String,
    val author: String,
    val year: Int,
    val description: String,
    val http: String,
    var isRented: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
): Parcelable