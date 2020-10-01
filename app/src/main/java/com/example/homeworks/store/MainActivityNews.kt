package com.example.homeworks.store

sealed class MainActivityNews

class ShowComputationError(val error: String) : MainActivityNews()
