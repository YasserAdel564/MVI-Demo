package com.mviarchitecture.app.data.api

import com.mviarchitecture.app.data.model.User
import retrofit2.http.GET

interface ApiService {

   @GET("users")
   suspend fun getUsers(): List<User>
}