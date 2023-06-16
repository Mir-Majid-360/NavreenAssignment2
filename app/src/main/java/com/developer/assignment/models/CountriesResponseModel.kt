package com.developer.assignment.models

import com.google.gson.annotations.SerializedName

class CountriesResponseModel {
    @SerializedName("status")
    var status: Boolean? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data: Data? = Data()
}

class Data {


    @SerializedName("countries")
    var countries: ArrayList<Country> = arrayListOf()

}

class Country {


    @SerializedName("country_id")
    var countryId: String? = null
    @SerializedName("country_name")
    var countryName: String? = null
    @SerializedName("country_code")
    var countryCode: String? = null
    @SerializedName("phone_code")
    var phoneCode: String? = null
    @SerializedName("iso_code_3")
    var isoCode3: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("image")
    var image: String? = null
    var isSelected : Boolean = false

}