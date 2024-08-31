package com.example.android_view_edu.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_view_edu.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopItemListLiveData.observe(this){
            Log.d("MainActivityTest", it.toString())
        }
        viewModel.getShopList()
        viewModel.deleteShopItem(5)
        viewModel.toggleEnabled(7)
    }
}
