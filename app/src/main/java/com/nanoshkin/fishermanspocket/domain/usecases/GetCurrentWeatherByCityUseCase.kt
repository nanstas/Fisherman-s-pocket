package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.weather.CurrentWeather
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository

class GetCurrentWeatherByCityUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): CurrentWeather {
        return weatherRepository.getCurrentWeatherByCity(cityName = cityName)
    }
}