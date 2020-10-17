package com.example.homeworks

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var btn1State: BUTTON_POSITION = BUTTON_POSITION.UP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClickListeners()
    }

    private fun initClickListeners() {
        btn_1.setOnClickListener {
            when (btn1State) {
                BUTTON_POSITION.UP -> showTextView()
                BUTTON_POSITION.DOWN -> hideTextView()
            }
        }
    }

    private fun showTextView() {
        initBtn1DownAnimation().apply btnAnimation@{
            doOnStart {
                btn1State = BUTTON_POSITION.DOWN

                initTvStatusShowAnimation().apply tvStatusShowAnimation@{
                    withStartAction {
                        tv_status.apply {
                            text = "Invisible mode deactivated"
                            visibility = View.VISIBLE
                        }
                    }
                    withEndAction {
                        initTvStatusHideAnimation().apply tvStatusHideAnimation@{
                            withEndAction {
                                tv_status.visibility = View.GONE
                            }
                            this@tvStatusHideAnimation.start()
                        }
                    }
                    this@tvStatusShowAnimation.start()
                }

                initTv1ClockAnimation(360f).apply tv1Animation@{
                    doOnStart {
                        tv_1.visibility = View.VISIBLE
                    }
                    this@tv1Animation.start()
                }
            }
            this@btnAnimation.start()
        }
    }

    private fun hideTextView() {
        initBtn1UpAnimation().apply btnAnimation@{
            initTvStatusShowAnimation().apply tvStatusShowAnimation@{
                withStartAction {
                    tv_status.apply {
                        text = "Invisible mode activated"
                        visibility = View.VISIBLE
                    }
                }
                withEndAction {
                    initTvStatusHideAnimation().apply tvStatusHideAnimation@{
                        withEndAction {
                            tv_status.visibility = View.GONE
                        }
                        this@tvStatusHideAnimation.start()
                    }
                }
                this@tvStatusShowAnimation.start()
            }

            initTv1ClockAnimation(-360f).apply tv1Animation@{
                doOnStart {
                    btn1State = BUTTON_POSITION.UP
                }
                doOnEnd {
                    tv_1.visibility = View.GONE
                    this@btnAnimation.start()
                }
                this@tv1Animation.start()
            }
        }
    }

    private fun initBtn1DownAnimation(): ObjectAnimator {
        return ObjectAnimator.ofFloat(btn_1, View.TRANSLATION_Y, 100f).apply {
            duration = 1000
        }
    }

    private fun initBtn1UpAnimation(): ObjectAnimator {
        return ObjectAnimator.ofFloat(btn_1, View.TRANSLATION_Y, 0f).apply {
            duration = 1000
        }
    }

    private fun initTv1ClockAnimation(degrees: Float): ValueAnimator {
        return ValueAnimator.ofFloat(degrees).apply {
            duration = 1000
            addUpdateListener { animation ->
                tv_1.rotation = animation.animatedValue as Float
            }
        }
    }

    private fun initTvStatusShowAnimation(): ViewPropertyAnimator =
        tv_status.animate().setDuration(500L).alpha(1f).scaleX(2f).scaleY(2f)


    private fun initTvStatusHideAnimation(): ViewPropertyAnimator =
        tv_status.animate().setDuration(500L).alpha(0f).scaleX(0f).scaleY(0f)


}
