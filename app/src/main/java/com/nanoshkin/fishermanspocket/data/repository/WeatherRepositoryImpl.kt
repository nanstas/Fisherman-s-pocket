package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.api.WeatherApi
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getCurrentWeatherByCity(cityName: String): CurrentWeather {

        val response = weatherApi.getCityDate(cityName = cityName)
        return response.body() ?: throw Exception()
    }
}