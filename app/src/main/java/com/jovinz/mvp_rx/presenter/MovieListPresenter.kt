package com.jovinz.mvp_rx.presenter

import com.jovinz.mvp_rx.model.MovieListModel
import com.jovinz.mvp_rx.utils.ApiKey
import com.jovinz.mvp_rx.utils.FilterData
import com.jovinz.mvp_rx.utils.SchedulerProvider
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
        val currentDisposable =
            movieListModel.execute(apiKey, 1, filterData)
                ?.subscribeOn(schedulerProvider.ioScheduler())
                ?.observeOn(schedulerProvider.uiScheduler())
                ?.subscribe({ response ->
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
