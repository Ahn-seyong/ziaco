package com.notepad96.apiretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.notepad96.apiretrofit.databinding.ActivityListMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListMain : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding: ActivityListMainBinding by lazy { ActivityListMainBinding.inflate(layoutInflater) }
    lateinit var listAdapter: ListAdapter
    lateinit var detailAdapter: DetailAdapter

    //SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {

                var d= s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

                listAdapter.filter.filter(d)


                return false
            }
        }

    var coinList = listOf<Coin>()
    var coinTickerList = listOf<CoinTicker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.currentUser.text = "${auth.currentUser?.email + "님 환영합니다."}"

        binding.searchviewCoin.setOnQueryTextListener(searchViewTextListener)

        binding.btnLogout.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

            auth.signOut()
        }

        listAdapter = ListAdapter()
        detailAdapter = DetailAdapter()

        binding.btn01.setOnClickListener {
            binding.coinExplain.visibility = View.VISIBLE
            binding.searchviewCoin.visibility = View.VISIBLE
            listAdapter.setList(coinList)
            listAdapter.notifyDataSetChanged()
        }

//        binding.recycler01.adapter = listAdapter
//        binding.recycler01.layoutManager = LinearLayoutManager(context)
//        binding.recycler01.setHasFixedSize(true)
//        아래 코드와 같다.

        binding.recycler01.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }




        initList()

    }


    private fun initList() {
        val call = ApiObject.getRetrofitService.getCoinAll()
        call.enqueue(object: Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT).show()
                if(response.isSuccessful) {
                    coinList = response.body() ?: listOf()
                    listAdapter.setList(coinList)
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

}