package com.example.homeworks.store

import java.lang.Exception

data class MainActivityState(
    val isLoading: Boolean = false,
    val values: Triple<String, String, String>? = null,
    val preLastChangedFields: Pair<String, String>? = null,
    val lastChangedFields: Pair<String, String>? = null,
    val error: Exception? = null
)
