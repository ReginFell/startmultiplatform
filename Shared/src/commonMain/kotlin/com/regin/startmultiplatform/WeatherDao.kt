package com.regin.startmultiplatform

import com.regin.startmultiplatform.db.model.WeatherModel

class WeatherDao(queryWrapper: QueryWrapper) {

    private val db = queryWrapper.weatherModelQueries

    internal fun insert(item: Weather) {
        db.insertItem(
            base = item.base,
            coordinate = item.coord
        )
    }

    internal fun select():List<WeatherModel> = db.selectAll().executeAsList()
}