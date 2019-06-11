package br.com.alex.marveltest.model

class ViewData<out T> private constructor(
    val state: ViewData.State,
    private val _data: T? = null,
    val exception: Throwable? = null
) {

    val data: T

        get() {
            return _data as T
        }

    enum class State {
        UNDEFINED, LOADING, SUCCESS, FAIL
    }

    companion object {
        fun <L> undefined() = ViewData<L>(State.UNDEFINED)

        fun <L> loading() = ViewData<L>(State.LOADING)

        fun <L> success(data: L) = ViewData<L>(State.SUCCESS, data)

        fun <L> fail(exception: Throwable) = ViewData<L>(State.FAIL, null, exception)
    }
}