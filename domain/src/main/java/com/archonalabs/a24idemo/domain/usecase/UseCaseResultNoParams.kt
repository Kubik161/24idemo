package com.archonalabs.a24idemo.domain.usecase

import com.archonalabs.a24idemo.domain.Result
/**
 * Created by Jakub Juroska on 10/14/20.
 */
abstract class UseCaseResultNoParams<out T : Any> : UseCase<Result<T>, Unit>() {

    suspend operator fun invoke() = super.invoke(Unit)

}