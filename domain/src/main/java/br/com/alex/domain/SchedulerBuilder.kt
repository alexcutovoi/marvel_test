package br.com.alex.domain

import io.reactivex.Scheduler

interface SchedulerBuilder {
    fun build(): Scheduler
}