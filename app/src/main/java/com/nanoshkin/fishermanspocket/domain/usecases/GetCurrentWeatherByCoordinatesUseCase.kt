package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository

class GetCurrentWeatherByCoordinatesUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): CurrentWeather {
        return weatherRepository.getCurrentWeatherByCoordinates(latitude = latitude, longitude = longitude)
    }
}