package com.example.homeworks.store

data class MainActivityState(
    val isLoading: Boolean = false,
    val values: Triple<String, String, String>? = null
)