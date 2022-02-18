package com.nanoshkin.fishermanspocket.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentWeatherBinding
import com.nanoshkin.fishermanspocket.presentation.viewmodels.WeatherViewModel
import com.nanoshkin.fishermanspocket.utils.Utils.formatDate
import com.nanoshkin.fishermanspocket.utils.Utils.formatTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private const val LOCATION_PERMISSION_REQ_CODE = 1000

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getCurrentLocation()

        lifecycleScope.launchWhenStarted {
            viewModel.currentWeatherSearchException.collectLatest {
                Toast.makeText(
                    requireContext(),
                    R.string.city_is_not_found,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.currentWeatherLoadException.collectLatest {
                Toast.makeText(
                    requireContext(),
                    R.string.toast_check_internet_availability,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.currentWeatherSomethingException.collectLatest {
                Toast.makeText(
                    requireContext(),
                    R.string.toast_something_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherBinding.bind(view)

        viewModel.currentWeather.observe(viewLifecycleOwner) {
            with(binding) {
                weatherCardView.visibility = View.VISIBLE

                cityTextView.text = it.name
                descriptionTextView.text = it.weather[0].description
                temperatureTextView.text = "${it.main.temp.toInt()}\u2103"
                val res = getWeatherImageRes(it.weather[0].icon)
                Glide.with(this@WeatherFragment)
                    .load(res)
                    .into(weatherImageView)
                humidityTextView.text = it.main.humidity.toString()
                pressureTextView.text = it.main.pressure.toString()
                windSpeedTextView.text = it.wind.speed.toInt().toString()
                val deg = it.wind.deg.toFloat()
                Glide.with(this@WeatherFragment)
                    .load(R.drawable.ic_wind_direction_24)
                    .into(windDirectionImageView)
                windDirectionImageView.rotation = deg + 180F
                dateTextView.text = formatDate(it.dt)
                sunriseTextView.text = formatTime(it.sys.sunrise)
                sunsetTextView.text = formatTime(it.sys.sunset)

                searchView.onActionViewCollapsed()
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

    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                viewModel.getCurrentWeatherByCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(
                        requireContext(), "You need to grant permission to access location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
