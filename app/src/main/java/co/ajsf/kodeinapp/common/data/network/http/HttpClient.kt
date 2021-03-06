package co.ajsf.kodeinapp.common.data.network.http

import co.ajsf.kodeinapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun httpClient(headersInterceptor: Interceptor, loggingInterceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder().addNetworkInterceptor(headersInterceptor).also {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(loggingInterceptor)
        }
    }.build()