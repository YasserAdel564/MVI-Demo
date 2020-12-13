package com.mviarchitecture.app.ui.users.intent

sealed class UserIntent {
    object FetchUser : UserIntent()
}