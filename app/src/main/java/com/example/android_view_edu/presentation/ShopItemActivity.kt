package com.example.android_view_edu.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android_view_edu.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        val mode = intent.getStringExtra(EXTRA_MODE)
        val itemId = intent.getStringExtra(SHOP_ITEM_ID)
        Log.d("ShopItemActivity_log", mode.toString() + ", item id: " + itemId.toString())
    }

    companion object {
        private val EXTRA_MODE = "extra_mode"
        private val CREATE_MODE = "create_mode"
        private val SHOP_ITEM_ID = "shop_item_id"
        private val EDIT_MODE = "edit_mode"

        fun getCreationModeIntent(context : Context) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, CREATE_MODE)
            return intent
        }

        fun getEditModeIntent(context: Context, shopItemId : Int) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EDIT_MODE)
            intent.putExtra(SHOP_ITEM_ID, shopItemId.toString())
            return  intent
        }


    }
}