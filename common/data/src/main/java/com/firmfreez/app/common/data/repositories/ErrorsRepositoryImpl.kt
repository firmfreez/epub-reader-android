package com.firmfreez.app.common.data.repositories

import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.common.ui.mappers.ErrorUiMapper
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

private const val TAG = "ErrorRepository"

@Single(binds = [ErrorsRepository::class])
class ErrorsRepositoryImpl(
    @Provided private val errorUiMapper: ErrorUiMapper,
    @Provided val logger: LoggerRepository,
) : ErrorsRepository {

    private val errorScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob() + CoroutineName(TAG))

    private val _errorsChannel = Channel<Throwable>(capacity = Channel.CONFLATED)

    override val errors: Flow<Throwable> = _errorsChannel.receiveAsFlow()

    override val localizedErrors: Flow<String> = errors.map(errorUiMapper::map)

    override fun tryEmit(error: Throwable) {
        logger.e(error)
        _errorsChannel.trySend(error)
    }

    override fun emit(error: Throwable) {
        logger.e(error)
        errorScope.launch {
            _errorsChannel.send(error)
        }
    }
}
