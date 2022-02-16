package com.nanoshkin.fishermanspocket.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.data.exceptions.ServerException
import com.nanoshkin.fishermanspocket.data.exceptions.UnknownException
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.usecases.GetCurrentWeatherByCityUseCase
import com.nanoshkin.fishermanspocket.domain.usecases.GetCurrentWeatherByCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherByCityUseCase: GetCurrentWeatherByCityUseCase,
    private val getCurrentWeatherByCoordinatesUseCase: GetCurrentWeatherByCoordinatesUseCase
) : ViewModel() {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    val currentWeatherLoadException = MutableSharedFlow<Unit>()
    val currentWeatherSomethingException = MutableSharedFlow<Unit>()

    fun getCurrentWeatherByCity(cityName: String) {
        viewModelScope.launch {
            try {
                _currentWeather.value = getCurrentWeatherByCityUseCase(cityName)!!
            } catch (e: ServerException) {
                currentWeatherLoadException.emit(Unit)
            } catch (e: UnknownException) {
                e.printStackTrace()
                currentWeatherSomethingException.emit(Unit)
            }
        }
    }

    fun getCurrentWeatherByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _currentWeather.value =
                    getCurrentWeatherByCoordinatesUseCase(
                        latitude = latitude,
                        longitude = longitude
                    )!!
            } catch (e: ServerException) {
                currentWeatherLoadException.emit(Unit)
            } catch (e: UnknownException) {
                currentWeatherSomethingException.emit(Unit)
            }
        }
    }
}