package com.arjun.nasaproject.service.repository

import com.arjun.nasaproject.service.model.Apod
import io.reactivex.Single
import org.joda.time.LocalDate

interface ApodRepository {

    fun getApod(date: LocalDate, highDefinition: Boolean): Single<Apod>
}
