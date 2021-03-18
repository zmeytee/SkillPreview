package ru.zmeytee.skillpreview.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geo(
    val lat: Double,
    val lng: Double
)