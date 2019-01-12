package co.ajsf.kodeinapp.common.domain.interactor

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UseCaseInvoker : Invoker, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override fun <Params, Type : Any> execute(
        useCase: UseCase<Params, Type>,
        params: Params,
        onResult: (Either<Error, Type>) -> Unit
    ) {
        val job = async(Dispatchers.IO) { useCase.run(params) }
        launch(Dispatchers.Main) { onResult.invoke(job.await()) }
    }
}