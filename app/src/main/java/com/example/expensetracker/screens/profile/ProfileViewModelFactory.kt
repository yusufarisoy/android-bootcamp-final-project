package com.example.expensetracker.screens.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileViewModelFactory(private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}