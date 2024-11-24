package com.example.weathernow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.network.RetrofitInstance
import com.example.weathernow.network.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city, "9d4bc55ec77ad07ad18df3371111922c")
                _weatherData.value = response
            } catch (e: Exception) {
                _weatherData.value = null
            }
        }
    }
}