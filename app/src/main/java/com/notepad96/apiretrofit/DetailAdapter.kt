package com.notepad96.apiretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notepad96.apiretrofit.databinding.ItemDetailBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailView>(){

    private var coinList = listOf<Coin>()
    private var coinTickerList = listOf<CoinTicker>()

    inner class DetailView(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root){

        fun load(position: Int) {
            binding.textDetailKor.text = coinList[position].korean_name
            binding.textDetailEng.text = coinList[position].english_name
            //binding.textDetailNowPrice.text = coinTickerList[position].tradePrice.toString()
        }

        fun setCoin(coin: Coin, coinTicker: CoinTicker) {
            binding.textDetailKor.text = "${coin.korean_name}"
            binding.textDetailEng.text = "${coin.english_name}"
            binding.textDetailNowPrice.text = "${coinTicker.tradePrice}"
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailView {
        val showDetail = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailView(showDetail)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: DetailView, position: Int) {
        val coin = coinList[position]
        val coinTicker = coinTickerList[position]
        holder.setCoin(coin, coinTicker)

        holder.load(position)

    }

    fun setList(list: List<Coin>) {
        coinList = list
    }

    fun setDetailList(list: List<CoinTicker>) {
        coinTickerList = list
    }

}