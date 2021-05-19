package com.mviarchitecture.app.ui.home.intent

sealed class UserIntent {
    object FetchUser : UserIntent()
}