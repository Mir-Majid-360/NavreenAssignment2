package com.developer.assignment.network

import com.developer.assignment.models.CountriesResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("getCountries")
    suspend fun  getCountries():Response<CountriesResponseModel>


}