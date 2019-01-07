package com.regin.startmultiplatform

import kotlinx.serialization.Serializable

@Serializable
data class Weather(val coord: Coordinate, val base: String)