package com.jovinz.mvp_rx.presenter

/**
 * Helper class for MVP pattern
 *
 * @param <T> - View class */
abstract class BasePresenter<T> {

    var view: T? = null
        private set

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    val isViewAttached: Boolean
        get() = view != null
}
