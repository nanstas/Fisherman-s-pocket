package com.nanoshkin.fishermanspocket.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentWeatherBinding
import com.nanoshkin.fishermanspocket.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherBinding.bind(view)

        viewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
            with(binding) {
                weatherCardView.visibility = View.VISIBLE
                cityTextView.text = currentWeather.name
                descriptionTextView.text = currentWeather.weather[0].description
                temperatureTextView.text = currentWeather.main.temp.toInt().toString()
                val res = getWeatherImageRes(currentWeather.weather[0].icon)
                Glide.with(this@WeatherFragment)
                    .load(res)
                    .into(weatherImageView)
                humidityTextView.text = currentWeather.main.humidity.toString()
                pressureTextView.text = currentWeather.main.pressure.toString()
                windSpeedTextView.text = currentWeather.wind.speed.toInt().toString()
                val deg = currentWeather.wind.deg.toFloat()
                Glide.with(this@WeatherFragment)
                    .load(R.drawable.ic_wind_direction_24)
                    .into(windDirectionImageView)
                windDirectionImageView.rotation = deg + 180F
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getCurrentWeatherByCity(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun getWeatherImageRes(input: String): Int =
        when (input) {
            "01d" -> R.drawable.ic_weather_01d
            "01n" -> R.drawable.ic_weather_01n
            "02d" -> R.drawable.ic_weather_02d
            "02n" -> R.drawable.ic_weather_02n
            "03d", "03n" -> R.drawable.ic_weather_03dn
            "04d", "04n" -> R.drawable.ic_weather_04dn
            "09d", "09n" -> R.drawable.ic_weather_09dn
            "10d" -> R.drawable.ic_weather_10d
            "10n" -> R.drawable.ic_weather_10n
            "11d", "11n" -> R.drawable.ic_weather_11dn
            "13d", "13n" -> R.drawable.ic_weather_13dn
            else -> R.drawable.ic_weather_50dn
        }
}
