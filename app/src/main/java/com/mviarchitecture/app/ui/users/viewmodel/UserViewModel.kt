package com.mviarchitecture.app.ui.users.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.mviarchitecture.app.data.repos.UserRepo
import com.mviarchitecture.app.ui.users.intent.UserIntent
import com.mviarchitecture.app.ui.users.state.UserStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepo()
    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UserStates>(UserStates.Idle)
    val state: StateFlow<UserStates>
        get() = _state


    init {
        handleIntent()
    }

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
                    UserStates.Success(repository.getUsers())
                } catch (e: Exception) {
                    UserStates.Error(e.localizedMessage)
                }
            } else {
                _state.value = UserStates.NoConnection
            }
        }
    }
}