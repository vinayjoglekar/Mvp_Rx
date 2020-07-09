package com.jovinz.mvp_rx.model

import com.jovinz.mvp_rx.network.ApiInterface
import com.jovinz.mvp_rx.utils.ApiKey
import com.jovinz.mvp_rx.utils.FilterData
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

open class MovieListModel @Inject constructor(private val apiInterface: ApiInterface) {

    fun execute(apiKey: ApiKey, pageNo: Int, filterData: FilterData): Single<MutableList<Result>>? {
        return apiInterface.getPopularMovies(apiKey = apiKey.apiKey, pageNo = pageNo)
            ?.flatMap { Observable.fromIterable(it.results) }
            ?.filter { it.originalLanguage.equals(filterData.language) }
            ?.toList()
    }
}
