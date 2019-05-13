package com.regin.startmultiplatform

import com.regin.startmultiplatform.db.model.WeatherModel

class WeatherDao(database: AnyNameDatabase) {

    private val db = database.weatherModelQueries

    internal fun insert(item: Weather) {
        db.insertItem(
            base = item.base,
            coordinate = item.coord
        )
    }

    internal fun select():List<WeatherModel> = db.selectAll().executeAsList()
}