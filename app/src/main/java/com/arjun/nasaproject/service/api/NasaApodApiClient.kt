package com.arjun.nasaproject.service.api

import com.arjun.nasaproject.service.model.Apod
import io.reactivex.Single

interface NasaApodApiClient {

    /**
     * @param key api key use your own or default "DEMO_KEY"
     * @param date format is yyyy-MM-dd
     * @param hdImage HD or no HD that is the question
     */
    fun getApod(
        key: String = "DEMO_KEY",
        date: String,
        hdImage: Boolean = false
    ): Single<Apod>
}