package com.intobeta.live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val onClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var list: List<Category> = emptyList()

    fun submitList(l: List<Category>) {
        list = l
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View, val tvName: TextView) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, view.findViewById(R.id.tvName))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.categoryName
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = list.size
}
