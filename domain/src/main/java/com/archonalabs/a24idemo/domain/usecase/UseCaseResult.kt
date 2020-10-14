package com.archonalabs.a24idemo.domain.usecase

import com.archonalabs.a24idemo.domain.Result
/**
 * Created by Jakub Juroska on 10/14/20.
 */
abstract class UseCaseResult<out T : Any, in Params> : UseCase<Result<T>, Params>()