package com.archonalabs.a24idemo.domain.usecase

/**
 * Created by Jakub Juroska on 10/14/20.
 */
abstract class UseCase<out T, in Params> {

    suspend operator fun invoke(params: Params): T = doWork(params)

    protected abstract suspend fun doWork(params: Params): T
}