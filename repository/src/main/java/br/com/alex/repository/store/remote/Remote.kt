package br.com.alex.repository.store.remote

import br.com.alex.marveltest.repository.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Remote {

    inline fun <reified T> getApi(baseUrl: String): T {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(getLoggingInterceptor())
        httpClientBuilder.addInterceptor { chain ->
            val req = chain.request()
            val httpUrl = req.url()

            //Required marvel params for all requests
            val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
            val newUrl = httpUrl.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                .addQueryParameter("hash", generateMD5Hash(ts, BuildConfig.PRIVATE_KEY+BuildConfig.PUBLIC_KEY))
                .build()

            chain.proceed(req.newBuilder().url(newUrl).build())
        }
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)

        val gson = GsonBuilder()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(T::class.java)
    }

    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        try {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } catch (e: Exception) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    fun generateMD5Hash(timeStamp: String, stringToHash: String): String{
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update((timeStamp+stringToHash).toByteArray())
            val messageDigest = digest.digest()
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}