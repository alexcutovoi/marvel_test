package br.com.alex.domain

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<T, Params> constructor(private val subscribeOnSchedulerBuilder: SchedulerBuilder) {

    private var disposables = CompositeDisposable()

    abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    fun execute(singleObserver: DisposableSingleObserver<T>, params: Params? = null) {
        val single = this.buildUseCaseSingle(params)
            .subscribeOn(subscribeOnSchedulerBuilder.build())
        disposables.add(single.subscribeWith(singleObserver))
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
            disposables = CompositeDisposable()
        }
    }
}