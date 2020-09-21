package com.example.infra.tx

import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

interface Tx {
    suspend operator fun <R> invoke(
        rollbackOnly: Boolean = false,
        block: suspend () -> R
    ): R
}

/**
 * NOTE: [executeAndAwait] has some issues with r2dbc-pool
 * `awaitFirstOrNull` does not guarantee the block returns after transaction completion
 */
@Service
internal class TxImpl(
    val rxtxOperator: TransactionalOperator
) : Tx {
    override suspend fun <R> invoke(rollbackOnly: Boolean, block: suspend () -> R): R {
        val nullMarker = Any()
        val nullMono = mono { nullMarker }
        val resultMono = mono<Any> { block() }

        val flux = rxtxOperator.execute { rxtx ->
            if (rollbackOnly) rxtx.setRollbackOnly()
            resultMono.switchIfEmpty(nullMono)
        }

        val result = flux.awaitLast()

        @Suppress("UNCHECKED_CAST")
        return result.takeIf { it != nullMarker } as R
    }
}