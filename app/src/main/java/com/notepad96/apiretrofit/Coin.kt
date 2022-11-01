package com.notepad96.apiretrofit

data class Coin(
    val market: String,
    val korean_name: String,
    val english_name: String
)

data class CoinDetail(
    val market: String,
    val candle_date_time_kst: String,
    val opening_price	: Double,
    val high_price: Double,
    val low_price: Double
)