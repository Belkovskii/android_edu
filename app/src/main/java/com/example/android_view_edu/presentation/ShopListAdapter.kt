package com.example.android_view_edu.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R
import com.example.android_view_edu.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemLongClickListener : ItemLongClickListener? = null

    var shopItemsList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class ShopItemViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        var tvName = view.findViewById<TextView>(R.id.tv_name)
        var tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    interface ItemLongClickListener {
        fun onItemLongClick(shopItem : ShopItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == 1) R.layout.item_shop_enabled else R.layout.item_shop_disabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int = shopItemsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = shopItemsList[position]
        if (holder is ShopItemViewHolder) {
            holder.tvName.text = item.name
            holder.tvCount.text = item.count.toString()
            holder.view.setOnLongClickListener{
                itemLongClickListener?.onItemLongClick(item)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopItemsList[position].enabled) 1 else 0
    }
}