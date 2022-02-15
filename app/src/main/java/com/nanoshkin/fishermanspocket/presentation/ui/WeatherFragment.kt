package com.nanoshkin.fishermanspocket.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentWeatherBinding
import com.nanoshkin.fishermanspocket.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherBinding.bind(view)

        viewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
            binding.weather.text = currentWeather.name
        }

        binding.searchButton.setOnClickListener {
            val city = binding.city.text.trim().toString()
            viewModel.getCurrentWeatherByCity(city)
        }
    }
}