package com.regin.startmultiplatform

class WeatherDao(queryWrapper: QueryWrapper) {

    private val db = queryWrapper.weatherQueries

    internal fun insert(item: Weather) {
        db.insertItem(
            base = item.base,
            coordinate = item.coord
        )
    }

    internal fun select() = db.selectAll()
}