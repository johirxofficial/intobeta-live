package com.intobeta.live.ui
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intobeta.live.R
import com.intobeta.live.model.Category
import com.intobeta.live.model.Stream
class CategoryAdapter(private val onClick:(Category)->Unit): RecyclerView.Adapter<CategoryAdapter.VH>(){
    private var list:List<Category> = emptyList()
    fun submitList(l:List<Category>){ list=l; notifyDataSetChanged() }
    class VH(v:View):RecyclerView.ViewHolder(v){ val tv:TextView=v.findViewById(R.id.tvName) }
    override fun onCreateViewHolder(p:ViewGroup, t:Int)=VH(LayoutInflater.from(p.context).inflate(R.layout.item_category,p,false))
    override fun getItemCount()=list.size
    override fun onBindViewHolder(h:VH, p:Int){ val c=list[p]; h.tv.text=c.categoryName; h.itemView.setOnClickListener{ onClick(c) } }
}
class StreamAdapter(private val onClick:(Stream)->Unit): RecyclerView.Adapter<StreamAdapter.VH>(){
    private var list:List<Stream> = emptyList()
    fun submitList(l:List<Stream>){ list=l; notifyDataSetChanged() }
    class VH(v:View):RecyclerView.ViewHolder(v){ val tv:TextView=v.findViewById(R.id.tvName); val iv:ImageView=v.findViewById(R.id.ivIcon) }
    override fun onCreateViewHolder(p:ViewGroup, t:Int)=VH(LayoutInflater.from(p.context).inflate(R.layout.item_stream,p,false))
    override fun getItemCount()=list.size
    override fun onBindViewHolder(h:VH, p:Int){ val s=list[p]; h.tv.text=s.name; Glide.with(h.itemView).load(s.streamIcon).into(h.iv); h.itemView.setOnClickListener{ onClick(s) } }
}
