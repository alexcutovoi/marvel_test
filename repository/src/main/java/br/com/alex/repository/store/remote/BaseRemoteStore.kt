package br.com.alex.repository.store.remote

import br.com.alex.repository.exception.NetworkConnectionException
import br.com.alex.repository.exception.RequestServerException
import br.com.alex.repository.exception.ServerException
import br.com.alex.repository.store.util.NetworkUtils
import io.reactivex.Single
import retrofit2.Response

open class BaseRemoteStore(private val networkUtils: NetworkUtils) {

    protected fun <T> makeBaseEntityApiRequest(request: () -> Single<Response<T>>): Single<T> {
        return if (networkUtils.isNetworkAvailable()) {
            request().map {
                if (it.isSuccessful && it.body() != null) {
                    if (it.code() == 200) {
                        it.body()!!
                    } else {
                        throw ServerException(it.message())
                    }
                } else {
                    throw RequestServerException(it)
                }
            }
        } else {
            Single.error(NetworkConnectionException())
        }
    }
}