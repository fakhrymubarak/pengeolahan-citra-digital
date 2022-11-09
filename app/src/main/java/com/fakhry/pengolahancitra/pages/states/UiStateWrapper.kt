package com.fakhry.pengolahancitra.pages.states

sealed class UiStateWrapper<out T> {
    data class Success<out T>(val data: T) : UiStateWrapper<T>()
    data class Loading(val isLoading: Boolean) : UiStateWrapper<Nothing>()
    data class Error(val message: String) : UiStateWrapper<Nothing>()
}