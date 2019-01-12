package co.ajsf.kodeinapp.common.di

import android.content.Context
import co.ajsf.kodeinapp.common.data.local.InMemoryPhotosDataSource
import co.ajsf.kodeinapp.common.data.network.UnsplashPhotosDataSource
import co.ajsf.kodeinapp.common.data.network.UnsplashService
import co.ajsf.kodeinapp.common.data.network.http.HeadersInterceptor
import co.ajsf.kodeinapp.common.data.network.http.httpClient
import co.ajsf.kodeinapp.common.data.network.http.loggingInterceptor
import co.ajsf.kodeinapp.common.domain.interactor.Invoker
import co.ajsf.kodeinapp.common.domain.interactor.UseCaseInvoker
import co.ajsf.kodeinapp.common.domain.repository.PhotosLocalDataSource
import co.ajsf.kodeinapp.common.domain.repository.PhotosNetworkDataSource
import co.ajsf.kodeinapp.logger.AndroidLogger
import co.ajsf.kodeinapp.logger.Logger
import co.ajsf.kodeinapp.photoslist.domain.repository.PhotosRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun appModule(appContext: Context) = Kodein.Module("appModule") {
    bind<Context>() with provider { appContext }
    bind<Logger>() with singleton { AndroidLogger() }
    bind<Invoker>() with singleton { UseCaseInvoker() }
    import(photosAppModule())
    import(httpAppModule())
}

fun httpAppModule() = Kodein.Module("httpModule") {
    bind<Interceptor>(tag = "headers") with singleton { HeadersInterceptor() }
    bind<Interceptor>(tag = "logging") with singleton { loggingInterceptor() }
    bind<OkHttpClient>() with singleton { httpClient(instance(tag = "headers"), instance(tag = "logging")) }
}

fun photosAppModule() = Kodein.Module("photoAppModule") {
    bind<UnsplashService>() with singleton {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .client(instance())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UnsplashService::class.java)
    }

    constant(tag = "ttl") with TimeUnit.HOURS.toMillis(1)
    bind<PhotosLocalDataSource>() with singleton { InMemoryPhotosDataSource(instance(tag = "ttl")) }
    bind<PhotosNetworkDataSource>() with singleton { UnsplashPhotosDataSource(instance()) }
    bind<PhotosRepository>() with singleton { PhotosRepository(instance(), instance()) }
}