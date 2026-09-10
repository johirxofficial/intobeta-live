package com.intobeta.live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class StreamAdapter(private val onClick: (Stream) -> Unit) :
    RecyclerView.Adapter<StreamAdapter.StreamViewHolder>() {

    private var list: List<Stream> = emptyList()

    fun submitList(l: List<Stream>) {
        list = l
        notifyDataSetChanged()
    }

    inner class StreamViewHolder(itemView: View, val tvName: TextView, val ivIcon: ImageView) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stream, parent, false)
        return StreamViewHolder(view, view.findViewById(R.id.tvName), view.findViewById(R.id.ivIcon))
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        if (item.streamIcon.isNotBlank()) {
            Glide.with(holder.itemView.context)
                .load(item.streamIcon)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivIcon)
        } else {
            holder.ivIcon.setImageResource(android.R.drawable.ic_menu_gallery)
        }
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = list.size
}
