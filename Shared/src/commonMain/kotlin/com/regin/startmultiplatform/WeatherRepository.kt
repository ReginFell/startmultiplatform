package com.regin.startmultiplatform

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {

    suspend fun fetchWeather(): Weather {
        val result = weatherApi.fetchWeather()
        weatherDao.insert(result)
        return result
    }

    fun selectFromDb() = weatherDao.select()
}