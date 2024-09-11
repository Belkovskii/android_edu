package com.example.android_view_edu.presentation

import androidx.lifecycle.ViewModel
import com.example.android_view_edu.data.ShopListRepositoryImpl
import com.example.android_view_edu.domain.DeleteShopItemUseCase
import com.example.android_view_edu.domain.EditShopItemUseCase
import com.example.android_view_edu.domain.GetShopListUseCase

class MainViewModel : ViewModel() {
    private val dataRepository = ShopListRepositoryImpl //неправильная реализация, т.к. не через DI
    // в слой Presentation НЕ ДОЛЖЕН ПОПАДАТЬ КЛАСС ИЗ СЛОЯ DATA, ЭТО НЕАРУШАЕТ ПРИНЦИП CLEAN ARCH.

    private val getShopListUseCase = GetShopListUseCase(dataRepository)

    private val deleteShopItemUseCase = DeleteShopItemUseCase(dataRepository)

    private val editShopItemUseCase = EditShopItemUseCase(dataRepository)

    val shopItemListLiveData  = GetShopListUseCase(dataRepository).getShopItemsList()

    fun getShopList() {
        getShopListUseCase.getShopItemsList()
    }

    fun deleteShopItem(id : Int) {
        val itemsList = getShopListUseCase.getShopItemsList()
        if (itemsList.value?.any { it.id == id } == true) {
            deleteShopItemUseCase.deleteShopItem(id)
        } else {
            throw Exception("no item with id $id")
        }
    }

    fun toggleEnabled(id : Int) {
        val itemsList = getShopListUseCase.getShopItemsList()
        val itemToToggle = itemsList.value?.find { it.id == id }
        if (itemToToggle != null) {
            val edited = itemToToggle.copy(enabled = !itemToToggle.enabled)
            editShopItemUseCase.editShopItem(edited)
        } else {
            throw Exception("no item with id $id")
        }
    }
}