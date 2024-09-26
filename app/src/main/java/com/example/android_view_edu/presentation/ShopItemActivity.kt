package com.example.android_view_edu.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.sax.TextElementListener
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var addItemButton: Button
    private lateinit var viewModel : ShopItemViewModel

    private var mode = UNDEFINED_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseExtras()
        initViews()
        setViewModelListeners()
        setInputTextChangeListeners()
        when (mode) {
            EDIT_MODE -> launchEditMode()
            CREATE_MODE -> launchCreateMode()
        }
    }

    private fun setInputTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputNameError()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCountError()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setViewModelListeners() {
        val context = this
        with(viewModel) {
            errorInputName.observe(context){
                etName.error = if (it) "Error" else null
            }
            errorInputCount.observe(context){
                etCount.error = if (it) "Error" else null
            }
            closeScreen.observe(context) {
                finish()
            }
        }
    }

    private fun launchEditMode() {
        viewModel.shopItem.observe(this){
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        viewModel.getShopItem(shopItemId)
        addItemButton.setOnClickListener{
            viewModel.editShopItem(
                etName.text.toString(),
                etCount.text.toString(),
                shopItemId
            )
        }
    }

    private fun launchCreateMode() {
        addItemButton.setOnClickListener{
            viewModel.createNewShopItem(
                etName.text.toString(),
                etCount.text.toString(),
            )
            if (etName.error == null && etCount.error == null) {
                this.finish()
            }
        }
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

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        addItemButton = findViewById(R.id.save_button)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
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