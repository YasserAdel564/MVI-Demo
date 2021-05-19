package com.mviarchitecture.app.data.model

import com.squareup.moshi.Json

data class VehicleModel(
    @Json(name = "key")
    val key: String ,
    @Json(name = "numberOfBids")
    val numberOfBids: Int,
    @Json(name = "commodity")
    val commodity: String ,
    @Json(name = "vehicleType")
    val vehicleType: String ,
    @Json(name = "vehicleType")
    val price: Int,
    @Json(name = "addresses")
    val addresses: List<Address>,
)
data class Address(
    @Json(name = "order")
    val order: Int,
    @Json(name = "key")
    val key: String,
    @Json(name = "latitude")
    val latitude: Double ,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "name")
    val name: String ,
)