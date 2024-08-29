package com.example.android_view_edu.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(editedItem : ShopItem)

    fun getShopItem(id : Int) : ShopItem?

    fun deleteShopItem(id : Int)

    fun getShopItemsList() : List<ShopItem>
}