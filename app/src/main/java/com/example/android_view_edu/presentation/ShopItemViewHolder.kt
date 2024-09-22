package com.example.android_view_edu.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_view_edu.R

class ShopItemViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    var tvName = view.findViewById<TextView>(R.id.tv_name)
    var tvCount = view.findViewById<TextView>(R.id.tv_count)
}