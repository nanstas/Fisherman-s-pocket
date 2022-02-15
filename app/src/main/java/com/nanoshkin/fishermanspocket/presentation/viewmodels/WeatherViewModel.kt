package com.nanoshkin.fishermanspocket.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.usecases.GetCurrentWeatherByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherByCityUseCase: GetCurrentWeatherByCityUseCase
) : ViewModel() {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    fun getCurrentWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _currentWeather.value = getCurrentWeatherByCityUseCase(cityName)!!
        }
    }
}