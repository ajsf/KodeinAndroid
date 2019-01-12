package co.ajsf.kodeinapp.common.domain.interactor

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error

abstract class UseCase<in Params, out ReturnType> where ReturnType : Any {
    abstract fun run(params: Params): Either<Error, ReturnType>
}