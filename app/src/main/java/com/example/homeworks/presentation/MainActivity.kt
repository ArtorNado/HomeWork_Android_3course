package com.example.homeworks.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.homeworks.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultNightMode(viewModel.getAppTheme())
        initDisplayModeSwitch()
        initClickListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.expression.observe(this, Observer {
            tv_write_number.text = it?.toString() ?: ""
        })
    }

    private fun initDisplayModeSwitch() {
        var defaultMode = getDefaultNightMode()

        displayModeSwitch.isChecked =
            (defaultMode == MODE_NIGHT_YES)

        displayModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            defaultMode = getDefaultNightMode()
            when (isChecked) {
                true -> if (defaultMode != MODE_NIGHT_YES) {
                    viewModel.setAppTheme(MODE_NIGHT_YES)
                    setDefaultNightMode(MODE_NIGHT_YES)
                }
                false -> if (defaultMode == MODE_NIGHT_YES) {
                    viewModel.setAppTheme(MODE_NIGHT_NO)
                    setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
        }
    }

    private fun initClickListeners() {
/*
        btn_0.setOnClickListener { viewModel.addNumber(getString(R.string.number_zero)) }
*/
        btn_1.setOnClickListener { viewModel.addNumber(getString(R.string.number_one)) }
        btn_2.setOnClickListener { viewModel.addNumber(getString(R.string.number_two)) }
        btn_3.setOnClickListener { viewModel.addNumber(getString(R.string.number_three)) }
/*        btn_4.setOnClickListener { viewModel.addNumber(getString(R.string.number_four)) }
        btn_5.setOnClickListener { viewModel.addNumber(getString(R.string.number_five)) }
        btn_6.setOnClickListener { viewModel.addNumber(getString(R.string.number_six)) }
        btn_7.setOnClickListener { viewModel.addNumber(getString(R.string.number_seven)) }
        btn_8.setOnClickListener { viewModel.addNumber(getString(R.string.number_eight)) }
        btn_9.setOnClickListener { viewModel.addNumber(getString(R.string.number_nine)) }*/
        btn_plus.setOnClickListener { viewModel.addOperator(getString(R.string.sign_plus)) }
/*        btn_minus.setOnClickListener { viewModel.addOperator(getString(R.string.sign_minus)) }
        btn_split.setOnClickListener { viewModel.addOperator(getString(R.string.sign_split)) }
        btn_multiply.setOnClickListener { viewModel.addOperator(getString(R.string.sign_multiply)) }
        btn_clear_all.setOnClickListener { viewModel.clearAll() }
        btn_clear_last.setOnClickListener { viewModel.cleatLast()}*/
        btn_equals.setOnClickListener { viewModel.solveExpression() }
    }

}
