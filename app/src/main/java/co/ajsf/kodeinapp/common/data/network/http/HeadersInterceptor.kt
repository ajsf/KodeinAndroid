package co.ajsf.kodeinapp.common.data.network.http

import co.ajsf.kodeinapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

class HeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", BuildConfig.apiKey)
            .build()
        return chain.proceed(request)
    }
}