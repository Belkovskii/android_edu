package com.example.android_view_edu.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewAdapter : ShopListAdapter
    private var shopItemFragmentContainer : FragmentContainerView? = null

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemFragmentContainer = findViewById(R.id.fragment_shop_items)
        setRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItemListLiveData.observe(this){
            recyclerViewAdapter.submitList(it)
        }
        val addButton = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        addButton.setOnClickListener{
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getCreationModeIntent(this)
                startActivity(intent)
            } else {
                attachFragment(ShopItemFragment.createAddModeFragment())
            }
        }
    }

    private fun attachFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.popBackStack()
        transaction
            .add(R.id.fragment_shop_items, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode() : Boolean {
        return shopItemFragmentContainer == null
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
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getEditModeIntent(this, it.id)
                startActivity(intent)
            } else {
                attachFragment(ShopItemFragment.createEditModeFragment(it.id))
            }

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
