package com.mviarchitecture.app.ui.users.state

import com.mviarchitecture.app.data.model.User

sealed class UserStates {
    object Idle : UserStates()
    object Loading : UserStates()
    object NoConnection : UserStates()
    data class Success(val user: List<User>) : UserStates()
    data class Error(val error: String?) : UserStates()
    object Empty : UserStates()

}