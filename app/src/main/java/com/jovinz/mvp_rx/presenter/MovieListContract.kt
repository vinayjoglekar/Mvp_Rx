package com.jovinz.mvp_rx.presenter

import com.jovinz.mvp_rx.model.Result


interface MovieListView {
    fun showProgress()
    fun hideProgress()
    fun setData(movies: List<Result>)
    fun onResponseFailure(throwable: Throwable?)

}