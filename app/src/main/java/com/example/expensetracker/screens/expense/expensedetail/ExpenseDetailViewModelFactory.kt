package com.example.expensetracker.screens.expense.expensedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.DatabaseDAO
import com.example.expensetracker.database.Expense

class ExpenseDetailViewModelFactory(private val dataSource: DatabaseDAO, private val application: Application, private val expense: Expense, private val type: Int) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(ExpenseDetailViewModel::class.java))
            return ExpenseDetailViewModel(dataSource, application, expense, type) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}