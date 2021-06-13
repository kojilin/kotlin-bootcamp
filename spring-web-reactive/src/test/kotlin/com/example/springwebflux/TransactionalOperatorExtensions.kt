package com.example.springwebflux

import kotlinx.coroutines.runBlocking
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

fun TransactionalOperator.runBlockingAndRollback(f: suspend () -> Unit) {
    runBlocking {
        executeAndAwait { tx ->
            f.invoke()
            tx.setRollbackOnly()
        }
    }
}
