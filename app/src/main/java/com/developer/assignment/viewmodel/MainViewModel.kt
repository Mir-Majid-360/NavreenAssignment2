package com.developer.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.assignment.models.Country
import com.developer.assignment.network.APIService
import com.developer.assignment.network.RetrofitInstance
import com.developer.assignment.repository.Repository
import com.developer.assignment.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    private var openDashboardFragment: MutableLiveData<Event<Array<Any>>> =
        MutableLiveData<Event<Array<Any>>>()

    private var openSelectCountriesFragment: MutableLiveData<Event<Array<Any>>> =
        MutableLiveData<Event<Array<Any>>>()

    private var countriesList: MutableLiveData<ArrayList<Country>> =
        MutableLiveData<ArrayList<Country>>()

    fun getCountries(): MutableLiveData<ArrayList<Country>> {
        return countriesList
    }

    private fun setCountriesData(list: ArrayList<Country>) {
        countriesList.postValue(list)
    }


    fun getOpenDashboardFragment(): MutableLiveData<Event<Array<Any>>> {
        return openDashboardFragment
    }

    fun setOpenDashboardFragment(objects: Array<Any>) {
        openDashboardFragment.value = Event(objects)
    }
    fun getOpenSelectCountriesFragment(): MutableLiveData<Event<Array<Any>>> {
        return openSelectCountriesFragment
    }

    fun setOpenSelectCountriesFragment(objects: Array<Any>) {
        openSelectCountriesFragment.value = Event(objects)
    }


    var repository: Repository

    /**
     *The CoroutineExceptionHandler is used to handle exceptions that occur within coroutines.
     *  It provides a way to define a centralized error handling mechanism for coroutines.
     **/
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        // provide this API from calling fragment  using dagger don't create object here
        //it is just for testing purpose
        val myAPI = RetrofitInstance.getRetrofitInstance().create(APIService::class.java)

        repository = Repository(myAPI)
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {


            val response = repository.getCountries()
            if (response.isSuccessful && response.code() == 200) {

                if (response.body()?.data != null)
                    setCountriesData(response.body()?.data?.countries!!)

            }
        }

    }
}