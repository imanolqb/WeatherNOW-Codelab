package com.example.weathernow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weathernow.databinding.MainScreenBinding
import com.example.weathernow.viewmodel.WeatherViewModel
import java.util.Calendar

class MainScreen : Fragment() {

    private var _binding: MainScreenBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val rootView = view.findViewById<View>(R.id.fragment_first_root)

        if (hour in 6..18) {
            rootView.setBackgroundResource(R.drawable.background_day)
        } else {
            rootView.setBackgroundResource(R.drawable.background_night)
        }

        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            if (weatherResponse != null) {
                binding.textViewCity.text = "City: ${weatherResponse.name}"
                binding.textViewTemperature.text = "Temperature: ${weatherResponse.main.temp}°C"
                binding.textViewWeatherDescription.text = "Description: ${weatherResponse.weather.firstOrNull()?.description}"
                binding.textViewHumidity.text = "Humidity: ${weatherResponse.main.humidity}%"
            } else {
                Toast.makeText(requireContext(), "Error fetching weather data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonFetchWeather.setOnClickListener {
            val city = binding.editTextCity.text.toString()
            if (city.isNotEmpty()) {
                weatherViewModel.fetchWeather(city)
            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}