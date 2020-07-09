package com.jovinz.mvp_rx.view

import android.os.Bundle
import android.util.Log
import com.jovinz.mvp_rx.R
import com.jovinz.mvp_rx.model.Result
import com.jovinz.mvp_rx.presenter.MovieListPresenter
import com.jovinz.mvp_rx.presenter.MovieListView
import com.jovinz.mvp_rx.utils.gone
import com.jovinz.mvp_rx.utils.visible
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), MovieListView {

    @Inject
    lateinit var movieListPresenter: MovieListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieListPresenter.attachView(this)
        movieListPresenter.requestDataFromServer( 1)
    }

    override fun showProgress() {
        progressMoviesListing.visible()
    }

    override fun hideProgress() {
        progressMoviesListing.gone()
    }

    override fun setData(movies: List<Result>) {
        val moviesList = StringBuilder()
        movies.forEach { result ->
            moviesList.append(
                "\n" +
                        "Title is ${result.title.toString()} and the language is" +
                        " ${result.originalLanguage.toString()}" +
                        "\n"
            )
        }
        tvMovies.text = moviesList.toString()
    }

    override fun onResponseFailure(throwable: Throwable?) {
        Log.d("MainActivity", throwable?.message.toString())
        tvMovies.text = throwable?.message.toString()
    }
}