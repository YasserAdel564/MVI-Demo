package com.mviarchitecture.app.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.mviarchitecture.app.data.repos.VehiclesRepo
import com.mviarchitecture.app.ui.home.intent.UserIntent
import com.mviarchitecture.app.ui.home.state.UserStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeVM @ViewModelInject
constructor(
    private val repository: VehiclesRepo,
) : ViewModel() {

    init {
        handleIntent()
    }

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UserStates>(UserStates.Idle)
    val state: StateFlow<UserStates>
        get() = _state

    var lat: Double? = null
    var lng: Double? = null


    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserIntent.FetchUser -> fetchUser()
                }
            }
        }
    }


    private fun fetchUser() {
        _state.value = UserStates.Loading
        viewModelScope.launch {
            if (NetworkUtils.isConnected()) {
                _state.value = try {
                    UserStates.Success(repository.getData(lat, lng))
                } catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                    UserStates.Error(e.localizedMessage)
                }
            } else {
                _state.value = UserStates.NoConnection
            }
        }
    }

    fun clearLocation() {
        lat = null
        lng = null
    }
}