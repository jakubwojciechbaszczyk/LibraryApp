package com.example.libraryapp.data.rent

import android.os.Parcelable
import androidx.room.*
import com.example.libraryapp.converters.PersonConverter
import com.example.libraryapp.converters.PositionConverter
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.position.Position
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity(tableName = "rent_table")
@TypeConverters(PositionConverter::class, PersonConverter::class)
@Parcelize
data class Rent(
    @Embedded
    val book: Position,
    @Embedded
    val person: Person,
    @ColumnInfo(name = "take")
    var take_date: LocalDateTime? = null,
    @ColumnInfo(name = "plan")
    var plan_return_date: LocalDateTime?  = null,
    @ColumnInfo(name = "return")
    var return_date: LocalDateTime? = null,
    @ColumnInfo(name = "status")
    var status: Boolean,
    @PrimaryKey(autoGenerate = true)
    val rental_id: Long = 0
): Parcelable