package com.nanoshkin.fishermanspocket.domain.repository

import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeatherByCity(cityName: String): CurrentWeather
    suspend fun getCurrentWeatherByCoordinates(latitude: Double, longitude: Double): CurrentWeather
}