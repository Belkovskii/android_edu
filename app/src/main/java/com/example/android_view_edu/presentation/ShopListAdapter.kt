package com.example.android_view_edu.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem
class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var itemLongClickListener : ((ShopItem) -> Unit)? = null
    var itemClickListener : ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == 1) R.layout.item_shop_enabled else R.layout.item_shop_disabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvName.text = item.name
        holder.tvCount.text = item.count.toString()
        holder.view.setOnLongClickListener{
            itemLongClickListener?.invoke(item)
            true
        }
        holder.view.setOnClickListener{
            itemClickListener?.invoke(item)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) 1 else 0
    }


}