package com.notepad96.apiretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.notepad96.apiretrofit.databinding.ItemDetailBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class ItemDetail : AppCompatActivity() {

    val binding:ItemDetailBinding by lazy { ItemDetailBinding.inflate(layoutInflater) }
    lateinit var listAdapter: ListAdapter
    lateinit var detailAdapter: DetailAdapter

    var coinList = listOf<Coin>()
    var coinTickerList = listOf<CoinTicker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listAdapter = ListAdapter()
        detailAdapter = DetailAdapter()

        binding.textDetailKor.text = intent.getStringExtra("korName")
        binding.textDetailEng.text = intent.getStringExtra("engName")

        initList()

        //val market = intent.getStringExtra("market")
        //loadData(market)
        binding.btnDetailBuy.setOnClickListener{
            Toast.makeText(this,"주문이 체결되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.BtnDetailCancle.setOnClickListener{
            Toast.makeText(this,"주문이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initList() {
        val call = ApiObject.getRetrofitService.getCoinDetail(intent.getStringExtra("market") ?: "emptyMarket")
        call.enqueue(object: Callback<List<CoinDetail>> {
            override fun onResponse(call: Call<List<CoinDetail>>, response: Response<List<CoinDetail>>) {
                Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT).show()
                if(response.isSuccessful) {
                    val tv =  response.body()?.get(0)?.opening_price?.toBigDecimal()?.toPlainString()
                    binding.textDetailNowPrice.text = "$tv BTC"
                    val hp =  response.body()?.get(0)?.high_price?.toBigDecimal()?.toPlainString()
                    binding.highPrice.text = "$hp BTC"
                    val lp =  response.body()?.get(0)?.low_price?.toBigDecimal()?.toPlainString()
                    binding.lowPrice.text = "$lp BTC"

//                    var total: Double = tv.toDouble()
//                    binding.totalbit.text = total * number
                    var min = findViewById<Button>(R.id.decreasecount)
                    var plus = findViewById<Button>(R.id.increasecount)
                    val outputtext = findViewById<TextView>(R.id.viewcount)
                    var number = BigDecimal(0);         // 데이터타입 BigDecimal로 더 크게 만듬
                    var vt = BigDecimal(tv)

                    /* 수량 */
                    binding.increasecount.setOnClickListener {
                        number++
                        outputtext.setText(number.toString())
                        // 합계
                        binding.totalbit.text = "${vt * number}bit"
                    }

                    binding.decreasecount.setOnClickListener {
                        if (number <= BigDecimal(0)) {
                            number = BigDecimal(0)
                            outputtext.setText(number.toString())
                        } else {
                            number--
                            outputtext.setText(number.toString())
                            binding.totalbit.text = "${vt * number} BTC"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CoinDetail>>, t: Throwable) {
                Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}