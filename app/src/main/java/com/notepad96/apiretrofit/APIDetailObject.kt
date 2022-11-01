package com.notepad96.apiretrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/* 없어도 되는 파일 */
object APIDetailObject {

    fun getAPIDetailObject() : DetailCoinListAPI {

        return Retrofit.Builder()
            .baseUrl("https://api.upbit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DetailCoinListAPI::class.java)
    }
}
