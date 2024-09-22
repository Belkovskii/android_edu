package com.example.android_view_edu.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.android_view_edu.domain.ShopItem

class ShopItemDiffCallback() : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean = (
            oldItem.id == newItem.id
            )

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean = (
            oldItem == newItem
            )
}