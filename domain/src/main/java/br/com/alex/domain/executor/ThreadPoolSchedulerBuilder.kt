package br.com.alex.domain.executor

import br.com.alex.domain.SchedulerBuilder
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadPoolSchedulerBuilder() : SchedulerBuilder {

    companion object {
        private const val CORE_POOL_SIZE = 2
        private const val MAXIMUM_POOL_SIZE = 4
        private const val KEEP_ALIVE_TIME = 1L
        private val TIME_UNIT = TimeUnit.MINUTES
        private var threadPoolExecutor: ThreadPoolExecutor? = null
    }

    override fun build(): Scheduler {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT, ArrayBlockingQueue(50, true)
            )
        }
        return Schedulers.from(threadPoolExecutor!!)
    }
}