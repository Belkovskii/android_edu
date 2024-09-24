package com.example.android_view_edu.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_view_edu.data.ShopListRepositoryImpl
import com.example.android_view_edu.domain.AddShopItemUseCase
import com.example.android_view_edu.domain.EditShopItemUseCase
import com.example.android_view_edu.domain.GetItemUseCase
import com.example.android_view_edu.domain.GetShopListUseCase
import com.example.android_view_edu.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val dataRepository = ShopListRepositoryImpl
    private val addUseCase : AddShopItemUseCase = AddShopItemUseCase(dataRepository)
    private val editUseCase : EditShopItemUseCase = EditShopItemUseCase(dataRepository)
    private val getItemUseCase : GetItemUseCase = GetItemUseCase(dataRepository)

    private val _itemsLiveData : MutableLiveData<List<ShopItem>> = GetShopListUseCase(dataRepository).getShopItemsList()
    val itemsLiveData : LiveData<List<ShopItem>>
        get() = _itemsLiveData

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem


    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen : LiveData<Unit>
        get() = _closeScreen

    private fun doCloseScreen() {
        _closeScreen.value = Unit
    }

    fun getShopItem(id : Int) {
        val shopItem = getItemUseCase.getShopItem(id)
        _shopItem.value = shopItem
    }

    fun createNewShopItem(inputName : String?, inputCount : String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            addUseCase.addShopItem(ShopItem(name, count, true))
            doCloseScreen()
        }
    }

    fun editShopItem(inputName : String?, inputCount : String?, id : Int) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            val item = _shopItem.value
            item?.let {
                editUseCase.editShopItem(it.copy(name = name, count = count))
                doCloseScreen()
            }
        }
    }

    private fun parseName(name : String?) : String {
        return name?.trim() ?: ""
    }

    private fun parseCount(countString : String?) : Int {
        return try {
            countString?.trim()?.toInt() ?: 0
        } catch (_ : Error) {
            0
        }
    }

    private fun validateInput(name : String, count : Int): Boolean {
        var result = true;
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true
        }
        if (count < 0) {
            result = false
            _errorInputCount.value = true
        }
        return result
    }

    fun resetInputNameError() {
        _errorInputName.value = false
    }

    fun resetInputCountError() {
        _errorInputCount.value = false
    }

}