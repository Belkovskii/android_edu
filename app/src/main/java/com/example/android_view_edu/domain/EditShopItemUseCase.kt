package com.example.android_view_edu.domain

class EditShopItemUseCase(private val repository : ShopListRepository) {

    fun editShopItem(editedItem : ShopItem) {
        repository.editShopItem(editedItem)
    }

}