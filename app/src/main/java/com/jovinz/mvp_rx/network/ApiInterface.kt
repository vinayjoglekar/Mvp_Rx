package com.jovinz.mvp_rx.network

import com.jovinz.mvp_rx.model.MoviesResponse
import com.jovinz.mvp_rx.model.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String?,
        @Query("page") pageNo: Int
    ): Observable<MoviesResponse>

}