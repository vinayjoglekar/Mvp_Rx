package com.jovinz.mvp_rx.presenter

import com.jovinz.mvp_rx.network.ApiClient
import com.jovinz.mvp_rx.network.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MovieListPresenter :
    BasePresenter<MovieListView>() {

    fun requestDataFromServer(apiKey: String, pageNo: Int) {
        view?.showProgress()
        val client = ApiClient.client?.create(ApiInterface::class.java)

        val currentDisposable =
            client?.getPopularMovies(apiKey = apiKey, pageNo = pageNo)
                ?.flatMap { it -> Observable.fromIterable(it.results) }
                ?.filter { it.originalLanguage.equals("en") }
                ?.toList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { response, error ->
                    if (!response.isNullOrEmpty()) {
                        view?.apply {
                            setData(response)
                            hideProgress()
                        }
                    } else {
                        error?.let {
                            view?.apply {
                                onResponseFailure(error)
                                hideProgress()
                            }
                        }
                    }
                }
    }


}
