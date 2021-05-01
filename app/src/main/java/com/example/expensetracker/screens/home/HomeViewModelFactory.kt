package com.example.expensetracker.screens.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.DatabaseDAO

class HomeViewModelFactory(private val dataSource: DatabaseDAO, private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}