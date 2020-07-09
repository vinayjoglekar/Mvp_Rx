package com.jovinz.mvp_rx.model


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") var page: Int?,
    @SerializedName("results") var results: List<Result>?,
    @SerializedName("total_pages") var totalPages: Int?,
    @SerializedName("total_results") var totalResults: Int?
)