package com.regin.startmultiplatform

import com.regin.startmultiplatform.db.model.WeatherModel
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

fun createDatabase(driver: SqlDriver): AnyNameDatabase {
    val coordinateAdapter = object : ColumnAdapter<Coordinate, String> {
        override fun decode(databaseValue: String): Coordinate {
            val split = databaseValue.split(":")
            return Coordinate(split[0].toFloat(), split[1].toFloat())
        }

        override fun encode(value: Coordinate): String {
            return "${value.lat}:${value.lon}"
        }
    }

    return AnyNameDatabase(
        driver,
        WeatherModel.Adapter(
            coordinateAdapter = coordinateAdapter
        )
    )
}
