package com.codeprogression.gameofcones

sealed class NetworkingViewState {
    class Init : NetworkingViewState()
    class Loading : NetworkingViewState()
    class Success<out T>(val item :T): NetworkingViewState()
    class Error(val errorMessage: String?): NetworkingViewState()
}