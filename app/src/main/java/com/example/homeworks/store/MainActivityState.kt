package com.example.homeworks.store

data class MainActivityState(
    val isLoading: Boolean = false,
    val values: Triple<String, String, String>? = null,
    val lastChandegFields: Pair<String, String>? = null
)