package com.example.infra.test.support

import com.example.infra.tx.Tx
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

open class RollbackTestSupport {
    @Autowired
    private lateinit var tx: Tx

    fun withRollback(block: suspend () -> Unit) {
        runBlocking {
            tx(rollbackOnly = true) { block() }
        }
    }
}