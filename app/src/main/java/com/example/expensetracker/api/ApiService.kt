package com.example.expensetracker.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//API KEY : e6075e86d5921af08910a634
private const val API_URL = "https://v6.exchangerate-api.com/v6/e6075e86d5921af08910a634/latest/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(API_URL).build()

interface ApiService
{
    @GET("EUR")
    fun getExchangeRates() : Call<ApiResponse>
}

object Api { val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) } }