package com.jovinz.mvp_rx.presenter

import com.jovinz.mvp_rx.model.MovieListModel
import com.jovinz.mvp_rx.model.Result
import com.jovinz.mvp_rx.utils.ApiKey
import com.jovinz.mvp_rx.utils.FilterData
import com.jovinz.mvp_rx.utils.SchedulerProvider
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

class MovieListPresenter @Inject constructor(
    private val filterData: FilterData,
    private var apiKey: ApiKey,
    private var schedulerProvider: SchedulerProvider,
    private var movieListModel: MovieListModel
) :
    BasePresenter<MovieListView>() {

    fun requestDataFromServer(pageNo: Int) {
        view?.showProgress()
        val singlePage =
            movieListModel.execute(apiKey, pageNo, filterData)
                ?.subscribeOn(schedulerProvider.ioScheduler())
                ?.observeOn(schedulerProvider.uiScheduler())

        updateData(singlePage!!)

    }

    fun requestTwoPagesFromServer() {
        val page1 = movieListModel.execute(apiKey, 1, filterData)
        val page2 = movieListModel.execute(apiKey, 2, filterData)

        val zipList = Single.zip(
            page1,
            page2,
            BiFunction { page1Result: List<Result>, page2Result: List<Result> ->
                val list = mutableListOf<Result>()
                list.addAll(page1Result)
                list.addAll(page2Result)
                list
            })
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())

        updateData(zipList)
    }

    fun requestThreePagesFromServer() {
        val page1 = movieListModel.execute(apiKey, 1, filterData)
        val page2 = movieListModel.execute(apiKey, 2, filterData)
        val page3 = movieListModel.execute(apiKey, 3, filterData)


        val zipList = Single.zip(
            page1,
            page2,
            page3,
            Function3 { page1Result: List<Result>, page2Result: List<Result>, page3Result: List<Result> ->
                val list = mutableListOf<Result>()
                list.addAll(page1Result)
                list.addAll(page2Result)
                list.addAll(page3Result)
                list
            })
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())

        updateData(zipList)
    }

    private fun updateData(result: Single<MutableList<Result>>) {
        val disposable = result.subscribe({ response ->
            if (!response.isNullOrEmpty()) {
                view?.apply {
                    setData(response)
                    hideProgress()
                }
            } else {
                view?.apply {
                    onResponseFailure(Throwable("EmptyList"))
                    hideProgress()
                }
            }
        }, { throwable ->
            view?.apply {
                onResponseFailure(throwable)
                hideProgress()
            }
        })
    }


}
