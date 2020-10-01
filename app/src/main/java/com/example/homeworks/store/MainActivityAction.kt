package com.example.homeworks.store

sealed class MainActivityAction

class WriteValues(val newValue: Pair<String, String>) : MainActivityAction()

class ComputationSuccess(val values: Triple<String, String, String>) : MainActivityAction()

object StartComputation : MainActivityAction()

object ErrorComputation : MainActivityAction()


