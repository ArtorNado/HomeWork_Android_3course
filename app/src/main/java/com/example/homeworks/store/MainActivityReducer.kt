package com.example.homeworks.store

import android.util.Log
import com.example.homeworks.common.exception.InsufficientDataException

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when (action) {
            is StartComputation -> state.copy(isLoading = true)
            is ComputationSuccess -> state.copy(isLoading = false, values = action.values)
            is WriteValues -> {
                state.apply {
                    if (lastChangedFields != null && lastChangedFields.first != action.newValue.first) {
                        return state.copy(
                            preLastChangedFields = lastChangedFields,
                            lastChangedFields = action.newValue,
                            values = null,
                            error = null
                        )
                    } else if (preLastChangedFields != null && lastChangedFields?.first == action.newValue.first
                        && preLastChangedFields.first != action.newValue.first
                    ) {
                        return state.copy(
                            preLastChangedFields = preLastChangedFields,
                            lastChangedFields = action.newValue,
                            values = null,
                            error = null
                        )
                    } else {
                        return state.copy(
                            preLastChangedFields = state.preLastChangedFields,
                            lastChangedFields = action.newValue,
                            error = InsufficientDataException,
                            values = null
                        )
                    }
                }
            }
            is ErrorComputation -> state.copy(isLoading = false)
        }
    }
}
