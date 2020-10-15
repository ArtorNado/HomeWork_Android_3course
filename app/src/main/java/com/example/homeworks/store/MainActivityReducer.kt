package com.example.homeworks.store

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when(action) {
            is StartComputation -> state.copy(isLoading = true)
            is ComputationSuccess -> state.copy(isLoading = false, values = action.values)
            is WriteValues -> {

                state.copy(values = null)
            }
            is ErrorComputation -> state.copy(isLoading = false)
        }
    }
}
