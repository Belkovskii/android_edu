package com.example.android_view_edu.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewAdapter : ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItemListLiveData.observe(this){
            recyclerViewAdapter.shopItemsList = it
        }
    }

    private fun setRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(recyclerView) {
            recyclerViewAdapter = ShopListAdapter()
            recyclerViewAdapter.itemLongClickListener = object : ShopListAdapter.ItemLongClickListener {
                override fun onItemLongClick(shopItem: ShopItem) {
                    viewModel.toggleEnabled(shopItem.id)
                }
            }
            adapter = recyclerViewAdapter
            recycledViewPool.setMaxRecycledViews(0, 10)
            recycledViewPool.setMaxRecycledViews(1, 10)

        }
    }

}
