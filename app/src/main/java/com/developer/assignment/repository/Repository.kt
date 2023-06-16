package com.developer.assignment.repository

import com.developer.assignment.models.CountriesResponseModel
import com.developer.assignment.network.APIService
import retrofit2.Response

class Repository(val apiService: APIService) {

    suspend fun getCountries(): Response<CountriesResponseModel> {
        return apiService.getCountries()
    }
}