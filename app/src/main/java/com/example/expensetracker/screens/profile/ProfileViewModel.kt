package com.example.expensetracker.screens.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_NAME: String = "userName"
private const val PREFERENCE_GENDER: String = "userGender"

class ProfileViewModel(application: Application) : ViewModel()
{
    private val sharedPreferences = application.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userGender = MutableLiveData<Int>()
    val userGender: LiveData<Int>
        get() = _userGender

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    init { getUser() }

    private fun getUser()
    {
        _userName.value = sharedPreferences.getString(PREFERENCE_NAME, "")
        _userGender.value = sharedPreferences.getInt(PREFERENCE_GENDER, -1)
    }

    fun saveChanges(name: String, gender: Int)
    {
        if(name != "")
        {
            with(sharedPreferences.edit()) {
                putString(PREFERENCE_NAME, name)
                putInt(PREFERENCE_GENDER, gender)
                apply()
            }

            _navigateToHome.value = true
        }
    }

    fun navigationDone() { _navigateToHome.value = null }
}