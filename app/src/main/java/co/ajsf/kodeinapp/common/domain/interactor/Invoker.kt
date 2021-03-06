package co.ajsf.kodeinapp.common.domain.interactor

import arrow.core.Either

import co.ajsf.kodeinapp.common.domain.error.Error

interface Invoker {

    fun <Params, Type : Any> execute(
        useCase: UseCase<Params, Type>,
        params: Params,
        onResult: (Either<Error, Type>) -> Unit
    )
}