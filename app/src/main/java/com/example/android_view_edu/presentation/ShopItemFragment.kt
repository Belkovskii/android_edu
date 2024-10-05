package com.example.android_view_edu.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout


class ShopItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var addItemButton: Button
    private lateinit var viewModel : ShopItemViewModel

    private var mode : String = UNDEFINED_MODE
    private var shopItemId : Int = ShopItem.UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
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
        with(viewModel) {
            errorInputName.observe(viewLifecycleOwner){
                etName.error = if (it) "Error" else null
            }
            errorInputCount.observe(viewLifecycleOwner){
                etCount.error = if (it) "Error" else null
            }
            closeScreen.observe(viewLifecycleOwner) {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun launchEditMode() {
        viewModel.shopItem.observe(viewLifecycleOwner){
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
        }
    }

    private fun parseParams() {
        val params = requireArguments()
        if (!params.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent")
        }
        mode = params.getString(SCREEN_MODE, UNDEFINED_MODE)
        if (mode != CREATE_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Undefined mode $mode")
        }
        if (mode == EDIT_MODE) {
            if (!params.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id is absent")
            }
            shopItemId = params.getInt(SHOP_ITEM_ID)
        }

    }

    private fun initViews(view : View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        addItemButton = view.findViewById(R.id.save_button)
    }

    companion object {
        private const val SCREEN_MODE = "screen_mode"
        private const val CREATE_MODE = "create_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNDEFINED_MODE = ""
        private const val SHOP_ITEM_ID = "shop_item_id"

        fun createAddModeFragment() : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, CREATE_MODE)
                }
            }
        }

        fun createEditModeFragment(shopItemId : Int) : ShopItemFragment {
            return ShopItemFragment().apply{
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }



}