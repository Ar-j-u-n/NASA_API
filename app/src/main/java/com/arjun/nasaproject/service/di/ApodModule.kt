package com.arjun.nasaproject.service.di

import com.arjun.nasaproject.BuildConfig
import com.arjun.nasaproject.service.api.NasaApodApi
import com.arjun.nasaproject.service.api.NasaApodApiClient
import com.arjun.nasaproject.service.api.NasaApodApiClientImpl
import com.arjun.nasaproject.service.manager.ApodManager
import com.arjun.nasaproject.service.manager.ApodManagerImpl
import com.arjun.nasaproject.service.repository.ApodRepository
import com.arjun.nasaproject.service.repository.ApodRepositoryImpl
import com.arjun.nasaproject.ui.detail.DetailAstronomyPictureViewModel
import com.arjun.nasaproject.ui.list.ListAstronomyPictureViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApodModule {

    private const val BASE_URL: String = "https://api.nasa.gov"

    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(59, TimeUnit.SECONDS)
            .readTimeout(59, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequests = 10
                maxRequestsPerHost = 10
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    val apodServiceModule: Module = module {
        single<ApodManager> { ApodManagerImpl(get()) }
        single<ApodRepository> { ApodRepositoryImpl(get()) }
        single<NasaApodApiClient> {
            NasaApodApiClientImpl(retrofit.create(NasaApodApi::class.java))
        }

        viewModel { ListAstronomyPictureViewModel(androidApplication(), get()) }
        viewModel { (date: String) ->
            DetailAstronomyPictureViewModel(androidApplication(), get(), date)
        }
    }

}