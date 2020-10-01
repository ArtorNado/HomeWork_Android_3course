package com.example.homeworks.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class CalculatorApi {

    companion object {
        private const val WORK_TIME = 5L
    }

    fun getFirstTerm(secondTerm: Int, sum: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple((sum - secondTerm), secondTerm, sum))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }

    fun getSecondTerm(firstTerm: Int, sum: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple(firstTerm, (sum - firstTerm), sum))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }

    fun getSum(firstTerm: Int, secondTerm: Int): Single<Triple<Int, Int, Int>> {
        return Single.just(Triple(firstTerm, secondTerm, (firstTerm + secondTerm)))
            .delay(WORK_TIME, TimeUnit.SECONDS)
    }

}
