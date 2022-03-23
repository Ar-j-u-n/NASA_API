package com.arjun.nasaproject.service.manager

import com.arjun.nasaproject.service.model.Apod
import io.reactivex.Single
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.joda.time.Period
import java.util.*

interface ApodManager {

    fun getApod(date: LocalDate, highDefinition: Boolean): Single<Apod>

    /**
     * retrieve the last [numberOfDays] APODs from [startDate] (in PDT timezone because NASA API is located to this timezone)
     */
    fun getLastApodList(
        numberOfDays: Int,
        startDate: LocalDate = LocalDate.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("PDT"))).minus(Period.days(1))
    ): Single<List<Apod>>

}