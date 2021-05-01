package com.example.expensetracker.screens.expense.expensedetail

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.DatabaseDAO
import com.example.expensetracker.database.Expense
import kotlinx.coroutines.launch

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_TRY: String = "currencyTRY"
private const val PREFERENCE_USD: String = "currencyUSD"
private const val PREFERENCE_GBP: String = "currencyGBP"

class ExpenseDetailViewModel(dataSource: DatabaseDAO, application: Application, selectedExpense: Expense, selectedType: Int) : ViewModel()
{
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    private val database = dataSource
    private val expense = selectedExpense
    private val type = selectedType

    private val _cost = MutableLiveData<String>()
    val cost: LiveData<String>
        get() = _cost

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    init {
        setExpenseDetail()
    }

    private fun setExpenseDetail()
    {
        val currencyTRY = sharedPreferences.getFloat(PREFERENCE_TRY, 9.95F)
        val currencyUSD = sharedPreferences.getFloat(PREFERENCE_USD, 1.21F)
        val currencyGBP = sharedPreferences.getFloat(PREFERENCE_GBP, 0.86F)

        val cost = when(type)
        {
            1 -> "%.0f".format(expense.cost * currencyTRY)
            2 -> "%.0f".format(expense.cost * currencyUSD)
            3 -> "%.0f".format(expense.cost * currencyGBP)
            else -> "%.0f".format(expense.cost)
        }
        val costString = when(type)
        {
            1 -> "$cost ₺"
            2 -> "$cost $"
            3 -> "$cost £"
            else -> "$cost €"
        }
        _cost.value = costString
    }

    fun delete()
    {
        viewModelScope.launch {
            database.delete(expense)
            navigateBackToHome()
        }
    }

    fun navigateBackToHome() { _navigateToHome.value = true }

    fun navigationDone() { _navigateToHome.value = null }
}