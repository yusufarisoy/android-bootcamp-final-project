package com.example.expensetracker.screens.expense.expensecreate

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.DatabaseDAO

class ExpenseCreateViewModelFactory(private val dataSource: DatabaseDAO, private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(ExpenseCreateViewModel::class.java))
            return ExpenseCreateViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}