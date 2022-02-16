package com.nanoshkin.fishermanspocket.data.api

import com.nanoshkin.fishermanspocket.BuildConfig
import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather/")
    suspend fun getCurrentWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") appId: String = BuildConfig.WEATHER_API_KEY
    ): Response<CurrentWeather>

    @GET("weather/")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") appId: String = BuildConfig.WEATHER_API_KEY
    ): Response<CurrentWeather>
}