package com.arjun.nasaproject.service.manager

import com.arjun.nasaproject.service.model.Apod
import com.arjun.nasaproject.service.repository.ApodRepository
import com.arjun.nasaproject.util.DateRangeDesc
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.LocalDate

class ApodManagerImpl(private val repository: ApodRepository) : ApodManager {

    override fun getApod(date: LocalDate, highDefinition: Boolean): Single<Apod> =
        repository.getApod(date, highDefinition)


    override fun getLastApodList(numberOfDays: Int, startDate: LocalDate): Single<List<Apod>> {
        if (numberOfDays <= 0) {
            return Single.just(emptyList())
        }

        val endDate = startDate.minusDays(numberOfDays - 1)
        val range = DateRangeDesc(startDate, endDate)

        return Observable.fromIterable(range)
            .flatMapSingle { date ->
                getApod(date, false)
            }
            .toList()
    }


}