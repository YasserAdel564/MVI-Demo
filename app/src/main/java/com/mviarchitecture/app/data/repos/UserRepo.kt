package com.mviarchitecture.app.data.repos

import com.mviarchitecture.app.data.api.RetrofitBuilder.apiService


class UserRepo {
    suspend fun getUsers() = apiService.getUsers()
}