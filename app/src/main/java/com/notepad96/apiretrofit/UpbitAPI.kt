package com.notepad96.apiretrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitAPI {
    @GET("v1/market/all")
    fun getCoinAll(
    ): Call<List<Coin>>

    @GET("v1/candles/minutes/5?count=1")
    fun getCoinDetail(
        @Query("market") marker: String
    ): Call<List<CoinDetail>>
}