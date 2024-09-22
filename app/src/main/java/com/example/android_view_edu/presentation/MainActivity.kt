package com.example.android_view_edu.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewAdapter : ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItemListLiveData.observe(this){
            recyclerViewAdapter.submitList(it)
        }
    }

    private fun setRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(recyclerView) {
            recyclerViewAdapter = ShopListAdapter()
            adapter = recyclerViewAdapter
            recycledViewPool.setMaxRecycledViews(0, 10)
            recycledViewPool.setMaxRecycledViews(1, 10)
        }
        setupClickListener()
        setupSwipeListener(recyclerView)
        setupLongClickListener()
    }

    private fun setupClickListener() {
        recyclerViewAdapter.itemClickListener = {
            Log.d(
                "click_listener",
                it.name + " -> " + it.count.toString()
            )
        }
    }

    private fun setupLongClickListener() {
        recyclerViewAdapter.itemLongClickListener = {
            viewModel.toggleEnabled(it.id)
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = recyclerViewAdapter.currentList[position]
                val id = item.id
                if (id > -1) {
                    viewModel.deleteShopItem(id)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

}
