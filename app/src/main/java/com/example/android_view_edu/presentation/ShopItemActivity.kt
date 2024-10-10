package com.example.android_view_edu.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private var mode = UNDEFINED_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onEditingFinished() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseExtras()
        if(savedInstanceState == null) {
            insertFragmentIntoContainer()
        }
    }

    private fun insertFragmentIntoContainer() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = when (mode) {
            EDIT_MODE -> ShopItemFragment.createEditModeFragment(shopItemId)
            CREATE_MODE -> ShopItemFragment.createAddModeFragment()
            else -> {throw RuntimeException("Wrong mode $mode")}
        }
        transaction.add(R.id.shop_item_container, fragment).commit()
    }

    private fun parseExtras() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Extra mode is absent")
        }
        mode = intent.getStringExtra(EXTRA_MODE).toString()
        if (mode != CREATE_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Undefined mode $mode")
        }
        if (mode == EDIT_MODE) {
            if (!intent.hasExtra(SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id is absent")
            }
            shopItemId = intent.getIntExtra(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private val EXTRA_MODE = "extra_mode"
        private val CREATE_MODE = "create_mode"
        private val SHOP_ITEM_ID = "shop_item_id"
        private val EDIT_MODE = "edit_mode"
        private const val UNDEFINED_MODE = ""

        fun getCreationModeIntent(context : Context) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, CREATE_MODE)
            return intent
        }

        fun getEditModeIntent(context: Context, shopItemId : Int) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EDIT_MODE)
            intent.putExtra(SHOP_ITEM_ID, shopItemId)
            return  intent
        }

    }

}