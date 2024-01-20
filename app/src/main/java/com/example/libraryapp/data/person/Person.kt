package com.example.libraryapp.data.person

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "person_table")
@Parcelize
data class Person (
    @ColumnInfo(name = "person")
    val name: String,
    val telephone: String,
    val photo: Bitmap?,

    @PrimaryKey(autoGenerate = true)
    val id_contact: Long = 0
): Parcelable