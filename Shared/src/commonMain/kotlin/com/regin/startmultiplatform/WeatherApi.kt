package com.regin.startmultiplatform

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.json.JSON

class WeatherApi(private val engine: HttpClientEngine) {

    private val client by lazy {
        HttpClient(engine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(JSON.nonstrict)
            }
        }
    }

    suspend fun fetchWeather(): Weather {
        return client.get {
            url("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
        }
    }

    companion object {
        private const val baseUrl = "https://samples.openweathermap.org"
    }
}