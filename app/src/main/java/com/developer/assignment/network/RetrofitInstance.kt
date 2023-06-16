package com.developer.assignment.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        const val BASE_URL = "https://vipankumar.com/SmartHealth/api/"
        fun getRetrofitInstance(): Retrofit {
            return  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}