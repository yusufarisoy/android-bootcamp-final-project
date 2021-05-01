package com.example.expensetracker.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "expenses")
data class Expense(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        val type: Int,
        val description: String,
        var cost: Float) : Parcelable