package com.notepad96.apiretrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/* 새 API 요청 - CoinTiker에 현재 가격 불러오기 위해서
*  삭제 가능한 파일 */
interface DetailCoinListAPI {
    @GET("v1/ticker")
    fun getCoinDetailList(market: String): Call<List<CoinTicker>>

    @GET("ticker/{market}")
    suspend fun detail(@Path("market") market: String) : Response<CoinTicker>
}

val client = OkHttpClient()

val request = Request.Builder()
    .url("https://api.upbit.com/v1/ticker")
    .get()
    .addHeader("accept", "application/json")
    .build()

val response = client.newCall(request).execute()