package com.mviarchitecture.app.data.api

import com.mviarchitecture.app.data.model.VehicleModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("marketplace")
    suspend fun getData(
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
    ): List<VehicleModel>
}