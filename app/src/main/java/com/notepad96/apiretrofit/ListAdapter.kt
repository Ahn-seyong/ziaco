package com.notepad96.apiretrofit

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.notepad96.apiretrofit.databinding.ItemListBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyView>(), Filterable {
    private var coinList = listOf<Coin>()

    var filteredCoins = ArrayList<Coin>()

    var itemFilter = ItemFilter()

    var coinDetailList = ArrayList<CoinTicker>()

    inner class MyView(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val coin: Coin = filteredCoins[adapterPosition]
            binding.text01.text = coin.korean_name
            binding.text02.text = coin.english_name
            binding.text03.text = coin.market

        }


        lateinit var coin: Coin

        /*fun changeTextColor() {

            if (coinDetailList[adapterPosition].openingPrice < coinDetailList[adapterPosition].tradePrice) {
                return coin.market.color
            }

        }
        */

        init {
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ItemDetail::class.java)
                intent.putExtra("korName", "${binding.text01.text}")
                intent.putExtra("engName", "${binding.text02.text}")
                intent.putExtra("market", "${binding.text03.text}")


                it.context.startActivity(intent)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int {
        return filteredCoins.size
    }

    fun setList(list: List<Coin>) {
        coinList = list
        filteredCoins.addAll(coinList)
    }

    override fun getFilter(): Filter{
        return itemFilter
    }

    inner class ItemFilter: Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            //????????? ???????????? ????????? ?????? ?????? ????????? ??????
            val filteredList: ArrayList<Coin> = ArrayList<Coin>()
            //???????????? ????????? ?????? ?????? ?????? -> ?????? ??????
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = coinList
                results.count = coinList.size

                return results
                //???????????? 2?????? ?????? ?????? -> ???????????? ??????????????? ??????
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (Coin in coinList) {
                    if (Coin.english_name.contains(filterString)) {
                        filteredList.add(Coin)
                    }
                }
                //??? ?????? ??????(???????????? 2?????? ??????) -> ?????? ????????????/???????????? ??????
            } else {
                for (Coin in coinList) {
                    if (Coin.english_name.contains(filterString) || Coin.market.contains(filterString)) {
                        filteredList.add(Coin)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredCoins.clear()
            filteredCoins.addAll(filterResults.values as ArrayList<Coin>)
            // ?????????????????? ????????????
            notifyDataSetChanged()
        }
    }

}