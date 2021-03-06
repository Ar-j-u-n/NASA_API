package com.arjun.nasaproject.service.api

import com.arjun.nasaproject.service.model.Apod
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApodApi {

    @GET("/planetary/apod")
    fun getApod(
        @Query("api_key") key: String,
        @Query("date") date: String,
        @Query("hd") hdImage: Boolean
    ): Single<Apod>
}