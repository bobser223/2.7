package com.example.dice

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class DiceModel {
    suspend fun rollDice(callback: (Int, Int) -> Unit) {
        coroutineScope {
            val jobs = List(5) { index ->
                async { rollSingleDie(index, callback) }
            }
            jobs.awaitAll()
        }
    }

    private suspend fun rollSingleDie(index: Int, callback: (Int, Int) -> Unit) {
        val rollingIterations = (15..30).random()

        repeat(rollingIterations) {
            val number = rollDie()
            callback(index, number)
        }
    }

    suspend fun rollDie(): Int {
        delay(100)
        return (1..6).random()
    }
}
