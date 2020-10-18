package com.example.homeworks.store.siteEffects

import com.example.homeworks.common.Constants
import com.example.homeworks.common.exception.LettersNotAllowedException
import com.example.homeworks.common.exception.UnexpectedException
import com.example.homeworks.data.CalculatorApi
import com.example.homeworks.store.*
import com.freeletics.rxredux.StateAccessor
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType
import java.lang.NumberFormatException

class WriteNumberSiteEffect(
    private val calculatorApi: CalculatorApi,
    private val newsRelay: Relay<MainActivityNews>
) : MainActivitySideEffect {

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<WriteValues>()
            .switchMap {
                if (state.invoke().error == null && state.invoke().lastChangedFields != null
                    && state.invoke().preLastChangedFields != null
                ) {
                    computation(
                        state.invoke().lastChangedFields!!,
                        state.invoke().preLastChangedFields!!
                    )
                        .map<MainActivityAction> {
                            ComputationSuccess(
                                Triple(
                                    it.first.toString(),
                                    it.second.toString(),
                                    it.third.toString()
                                )
                            )
                        }
                        .doOnError { throwable ->
                            when (throwable) {
                                LettersNotAllowedException -> newsRelay.accept(
                                    ShowComputationError(
                                        throwable.message.toString()
                                    )
                                )
                            }
                        }
                        .onErrorReturnItem(ErrorComputation)
                        .toObservable()
                        .startWith(StartComputation)
                } else {
                    Single.just(ErrorComputation)
                        .toObservable()
                }
            }
    }

    private fun computation(
        lastValue: Pair<String, String>,
        preLastValue: Pair<String, String>
    ): Single<Triple<Int, Int, Int>> {
        try {
            val lastNumber = Integer.parseInt(lastValue.second)
            val preLastNumber = Integer.parseInt(preLastValue.second)
            return when (lastValue.first) {
                Constants.FIRST_TERM -> {
                    when (preLastValue.first) {
                        Constants.SECOND_TERM -> {
                            calculatorApi.getSum(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        Constants.SUM -> {
                            calculatorApi.getSecondTerm(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                Constants.SECOND_TERM -> {
                    when (preLastValue.first) {
                        Constants.FIRST_TERM -> {
                            calculatorApi.getSum(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        Constants.SUM -> {
                            calculatorApi.getFirstTerm(
                                lastNumber,
                                preLastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                Constants.SUM -> {
                    when (preLastValue.first) {
                        Constants.FIRST_TERM -> {
                            calculatorApi.getSecondTerm(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        Constants.SECOND_TERM -> {
                            calculatorApi.getFirstTerm(
                                preLastNumber,
                                lastNumber
                            )
                        }
                        else -> {
                            Single.error(UnexpectedException)
                        }
                    }
                }
                else -> {
                    Single.error(UnexpectedException)
                }
            }
        } catch (e: NumberFormatException) {
            return Single.error(LettersNotAllowedException)
        }
    }

}
