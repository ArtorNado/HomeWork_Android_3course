package com.example.homeworks.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homeworks.utills.append
import com.example.homeworks.utills.eval
import com.example.homeworks.preference.ThemeSharedPreference

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _expression = MutableLiveData<String>()
    val expression: LiveData<String> = _expression

    private val themeSharedPreferences = ThemeSharedPreference(getApplication())

    private val operators = listOf("/", "*", "-", "+")

    fun setAppTheme(theme: Int) {
        themeSharedPreferences.appTheme = theme
    }

    fun getAppTheme() = themeSharedPreferences.appTheme

    fun addNumber(number: String) {
        val currentExpression = expression.value ?: ""
        _expression.value = currentExpression.append(number)
    }

    fun addOperator(operator: String) {
        val currentExpression = expression.value ?: ""
        if (!isLastSignOperator(currentExpression)) {
            _expression.value = currentExpression.append(operator)
        } else {
            val expressionWithoutLast = currentExpression.dropLast(1)
            _expression.value = expressionWithoutLast.append(operator)
        }
    }

    fun clearAll() {
        _expression.value = null
    }

    fun cleatLast() {
        val currentExpression = expression.value ?: ""
        _expression.value = currentExpression.dropLast(1)
    }

    fun solveExpression() {
        val currentExpression = expression.value ?: ""
        if (!isLastSignOperator(currentExpression)) _expression.value =
            currentExpression.eval().toString()
    }

    private fun isLastSignOperator(expression: String): Boolean {
        val lastSign = expression.last().toString()
        return operators.find { it == lastSign } != null
    }
}