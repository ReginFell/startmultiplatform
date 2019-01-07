package com.regin.startmultiplatform

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.regin.startmultiplatform.db.model.WeatherModel
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqlDatabase
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Stetho.initializeWithDefaults(this)

        val engine = OkHttpEngine(OkHttpConfig().apply {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        })

        val coordinateAdapter = object: ColumnAdapter<Coordinate, String> {
            override fun decode(databaseValue: String): Coordinate {
                val split = databaseValue.split(":")
                return Coordinate(split[0].toFloat(), split[1].toFloat())
            }

            override fun encode(value: Coordinate): String {
                return "${value.lat}:${value.lon}"
            }
        }

        val weatherDao = WeatherDao(QueryWrapper(
            AndroidSqlDatabase(QueryWrapper.Schema, this, "database.db"),
            WeatherModel.Adapter(coordinateAdapter)
        ))

        val weatherApi = WeatherApi(engine)
        val weatherRepository = WeatherRepository(weatherApi, weatherDao)
        launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) { weatherRepository.fetchWeather() }
                //Toast.makeText(this@MainActivity, result.toString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }

        launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {weatherRepository.selectFromDb()}
            Toast.makeText(this@MainActivity, "result from db ${result.toString()}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
