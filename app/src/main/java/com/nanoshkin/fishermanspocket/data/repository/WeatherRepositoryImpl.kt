package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.api.WeatherApi
import com.nanoshkin.fishermanspocket.data.exceptions.ApiException
import com.nanoshkin.fishermanspocket.data.exceptions.SearchException
import com.nanoshkin.fishermanspocket.data.exceptions.ServerException
import com.nanoshkin.fishermanspocket.data.exceptions.UnknownException
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getCurrentWeatherByCity(cityName: String): CurrentWeather {
        try {
            val response = weatherApi.getCurrentWeatherByCity(cityName = cityName)
            if (!response.isSuccessful && response.code() == 404) {
                throw SearchException()
            }
            return response.body() ?: throw ApiException(response.code(), response.message())
        } catch (e: SearchException) {
            throw SearchException()
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun getCurrentWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): CurrentWeather {
        try {
            val response = weatherApi.getCurrentWeatherByCoordinates(
                latitude = latitude,
                longitude = longitude
            )
            return response.body() ?: throw ApiException(response.code(), response.message())
        } catch (e: IOException) {
            throw ServerException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}