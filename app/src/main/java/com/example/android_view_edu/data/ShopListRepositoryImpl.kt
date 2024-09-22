package com.example.android_view_edu.data

import androidx.lifecycle.MutableLiveData
import com.example.android_view_edu.domain.ShopItem
import com.example.android_view_edu.domain.ShopListRepository
import java.util.TreeSet
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private var shopItemsList : TreeSet<ShopItem>
    val shopItemsListLiveData = MutableLiveData<List<ShopItem>>()

    init {
        val comparator = Comparator<ShopItem>{ item1, item2 ->
            item1.id.compareTo(item2.id)
        }
        shopItemsList = TreeSet<ShopItem>(comparator)
        for (i in 0 until 10) {
            val item = ShopItem("Test name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItemsList.add(shopItem)
        updateLiveData()
    }

    override fun editShopItem(editedItem: ShopItem) {
        val oldItem = shopItemsList.find{it.id == editedItem.id}
        shopItemsList.remove(oldItem)
        addShopItem(editedItem)
    }

    override fun getShopItem(id: Int): ShopItem =  shopItemsList.find {
        it.id == id
    } ?: throw RuntimeException("Shop Item with id $id not found")

    override fun deleteShopItem(id: Int) {
        val itemToRemove = shopItemsList.find { it.id == id }
        shopItemsList.remove(itemToRemove)
        updateLiveData()
    }

    override fun getShopItemsList(): MutableLiveData<List<ShopItem>> {
        return shopItemsListLiveData
    }

    private fun updateLiveData() {
        shopItemsListLiveData.value = shopItemsList.toList()
    }

}