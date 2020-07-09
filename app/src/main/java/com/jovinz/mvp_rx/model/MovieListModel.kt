package com.jovinz.mvp_rx.model
//
//import com.jovinz.mvp_rx.network.ApiClient.client
//import com.jovinz.mvp_rx.network.ApiInterface
//import com.jovinz.mvp_rx.presenter.MovieListContract
//import io.reactivex.Observable
//import io.reactivex.SingleObserver
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.Disposable
//import io.reactivex.observers.DisposableObserver
//import io.reactivex.schedulers.Schedulers
//import java.util.stream.Collectors.toList
//
//
//class MovieListModel : MovieListContract.Model {
//    override fun getMovieList(
//        onFinishedListener: MovieListContract.Model.OnFinishedListener?,
//        pageNo: Int
//    ) {
//        val client = client?.create(ApiInterface::class.java)
//
//        val currentDisposable =
//            client?.getPopularMovies(apiKey = "0ba1ead43a849d61114e60ff7322b791", pageNo = 1)
//                ?.flatMap { it -> Observable.fromIterable(it.results) }
//                ?.filter { it.originalLanguage.equals("en") }
//                ?.toList()
//                ?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
//                ?.subscribe { response, error ->
//                    if (!response.isNullOrEmpty()) {
//                        onFinishedListener?.onFinished(response)
//                    } else {
//                        error?.let { onFinishedListener?.onFailure(error) }
//                    }
//                }
//    }
//
//}