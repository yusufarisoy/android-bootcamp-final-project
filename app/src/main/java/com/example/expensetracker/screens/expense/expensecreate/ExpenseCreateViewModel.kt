package com.example.expensetracker.screens.expense.expensecreate

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.DatabaseDAO
import com.example.expensetracker.database.Expense
import kotlinx.coroutines.launch
import java.lang.Exception

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_TRY: String = "currencyTRY"
private const val PREFERENCE_USD: String = "currencyUSD"
private const val PREFERENCE_GBP: String = "currencyGBP"

class ExpenseCreateViewModel(dataSource: DatabaseDAO, application: Application) : ViewModel()
{
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)

    private val database = dataSource
    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun createExpense(type: Int, description: String, currencyType: Int, costText: String)
    {
        try
        {
            if(type != -1 && description != "" && costText != "")
            {
                var cost = costText.toFloat()
                if(cost >= 0)
                {
                    val currencyTRY: Float = sharedPreferences.getFloat(PREFERENCE_TRY, 9.95F)
                    val currencyUSD = sharedPreferences.getFloat(PREFERENCE_USD, 1.21F)
                    val currencyGBP = sharedPreferences.getFloat(PREFERENCE_GBP, 0.86F)
                    cost /= when(currencyType)
                    {
                        1 -> currencyTRY
                        2 -> currencyUSD
                        3 -> currencyGBP
                        else -> 1f
                    }

                    viewModelScope.launch {
                        val expense = Expense(0L, type, description, cost)
                        insert(expense)
                        _navigateToHome.value = true
                    }
                }
            }
        }
        catch(ex: Exception) { Log.e("ERROR", "ExpenseCreateViewModel - createExpense()") }
    }

    private suspend fun insert(expense: Expense) { database.insert(expense) }

    fun navigationDone() { _navigateToHome.value = null }
}