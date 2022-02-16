package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.api.WeatherApi
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository
import com.nanoshkin.fishermanspocket.utils.Utils.makeRequest
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getCurrentWeatherByCity(cityName: String): CurrentWeather = makeRequest(
        request = { weatherApi.getCurrentWeatherByCity(cityName = cityName) },
        onSuccess = { body ->
            body
        }
    )

    override suspend fun getCurrentWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): CurrentWeather = makeRequest(
        request = {
            weatherApi.getCurrentWeatherByCoordinates(
                latitude = latitude,
                longitude = longitude
            )
        },
        onSuccess = { body ->
            body
        }
    )
}