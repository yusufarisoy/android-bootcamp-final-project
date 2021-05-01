package com.example.expensetracker.screens.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.api.Api
import com.example.expensetracker.api.ApiResponse
import com.example.expensetracker.database.DatabaseDAO
import com.example.expensetracker.database.Expense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val PREFERENCES_FILE: String = "SharedPreferences"
private const val PREFERENCE_NAME: String = "userName"
private const val PREFERENCE_GENDER: String = "userGender"
private const val PREFERENCE_TRY: String = "currencyTRY"
private const val PREFERENCE_USD: String = "currencyUSD"
private const val PREFERENCE_GBP: String = "currencyGBP"

class HomeViewModel(dataSource: DatabaseDAO, applicationMain: Application) : ViewModel()
{
    private val application = applicationMain
    private var sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    private val database = dataSource
    var expenses = database.getAllExpenses()

    private var _showConnectionStatus = MutableLiveData<Boolean?>()
    val showConnectionStatus: LiveData<Boolean?>
        get() = _showConnectionStatus

    private var _currentCurrency = MutableLiveData(1)
    val currentCurrency: LiveData<Int>
        get() = _currentCurrency

    private val currencyTRY: Float = sharedPreferences.getFloat(PREFERENCE_TRY, 9.95F)
    private var currencyUSD = sharedPreferences.getFloat(PREFERENCE_USD, 1.21F)
    private var currencyGBP = sharedPreferences.getFloat(PREFERENCE_GBP, 0.86F)

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _userGender = MutableLiveData<Int>()
    val userGender: LiveData<Int>
        get() = _userGender

    private val _totalExpense = MutableLiveData<String>()
    val totalExpense: LiveData<String>
        get() = _totalExpense

    private val _navigateToExpenseDetail = MutableLiveData<Expense>()
    val navigateToExpenseDetail: LiveData<Expense>
        get() = _navigateToExpenseDetail

    init {
        getUser()
        updateExchangeRates()
    }

    private fun getUser()
    {
        _userName.value = sharedPreferences.getString(PREFERENCE_NAME, "")
        _userGender.value = sharedPreferences.getInt(PREFERENCE_GENDER, -1)
    }

    fun getCurrentCurrency(): Int { return this._currentCurrency.value?:1 }

    fun setCurrentCurrency(newRate: Int) { this._currentCurrency.value = newRate }

    fun getCurrentExchangeRate(): Float
    {
        return when(this._currentCurrency.value) {
            1 -> currencyTRY
            2 -> currencyUSD
            3 -> currencyGBP
            else -> 1f
        }
    }

    fun calculateTotalExpense(sumFloat: Float)
    {
        val sum = when(getCurrentCurrency())
        {
            1 -> "%.0f".format(sumFloat * currencyTRY)
            2 -> "%.0f".format(sumFloat * currencyUSD)
            3 -> "%.0f".format(sumFloat * currencyGBP)
            else -> "%.0f".format(sumFloat)
        }
        _totalExpense.value = when(getCurrentCurrency())
        {
            1 -> "$sum ₺"
            2 -> "$sum $"
            3 -> "$sum £"
            else -> "$sum €"
        }
    }

    private fun updateExchangeRates()
    {
        Api.retrofitService.getExchangeRates().enqueue(
                object: Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        with(sharedPreferences.edit())
                        {
                            response.body()?.conversion_rates?.TRY?.let { putFloat(PREFERENCE_TRY, it) }
                            response.body()?.conversion_rates?.USD?.let { putFloat(PREFERENCE_USD, it) }
                            response.body()?.conversion_rates?.GBP?.let { putFloat(PREFERENCE_GBP, it) }
                            apply()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable)
                    {
                        _showConnectionStatus.value = true
                        Log.e("API ERROR", t.message?:"Unknown Error")
                    }
                }
        )
    }

    fun connectionStatusShowDone() { _showConnectionStatus.value = null }

    fun onExpenseClicked(expense: Expense) { _navigateToExpenseDetail.value = expense }

    fun navigationDone() { _navigateToExpenseDetail.value = null }
}