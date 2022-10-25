package ru.mrpotz.fellowcar.utils

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun tickerFlow(
    periodMillis: Long,
    delayMillis: Long = 0L,
    startAtMillis: Long,
    durationMillis: Long? = null,
): Flow<Long> = flow {
    delay(delayMillis)
    var accumulatedDurationMillis = startAtMillis
    while (durationMillis == null || (accumulatedDurationMillis < durationMillis)) {
        val startLoopCurrentTimeMillis = System.currentTimeMillis()
        emit(accumulatedDurationMillis)
        delay(periodMillis)
        accumulatedDurationMillis += System.currentTimeMillis() - startLoopCurrentTimeMillis
    }
}

// https://github.com/Kotlin/kotlinx.coroutines/issues/1302
@OptIn(FlowPreview::class)
fun <T> Flow<T>.timedChunk(periodMs: Long): Flow<List<T>> {
    val chunkLock = ReentrantLock()
    var chunk = mutableListOf<T>()

    return flow {
        val flowEmitter: FlowCollector<List<T>> = this
        collect { item: T ->
            // the chunk is reused before it's collected by "sample()"
            val localChunk = chunkLock.withLock {
                chunk.add(item)
                chunk.toList()
            }
            if (localChunk.isNotEmpty()) {
                flowEmitter.emit(localChunk)
            }
        }
    }.sample(periodMs)
        .transform {
            if (it.isNotEmpty()) {
                emit(it)
            }
            chunkLock.withLock {
                chunk = mutableListOf()
            }
        }
}
