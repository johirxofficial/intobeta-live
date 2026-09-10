
package com.intobeta.live.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intobeta.live.databinding.ItemCategoryBinding
import com.intobeta.live.databinding.ItemStreamBinding
import com.intobeta.live.model.Category
import com.intobeta.live.model.LiveStream

class CategoryAdapter(val list: List<Category>, val onClick: (Category)->Unit): RecyclerView.Adapter<CategoryAdapter.VH>(){
    class VH(val b: ItemCategoryBinding): RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, vt: Int)=VH(ItemCategoryBinding.inflate(LayoutInflater.from(p.context),p,false))
    override fun getItemCount()=list.size
    override fun onBindViewHolder(h: VH, pos: Int){ h.b.tvName.text=list[pos].categoryName; h.b.root.setOnClickListener{ onClick(list[pos]) } }
}
class StreamAdapter(val list: List<LiveStream>, val onClick: (LiveStream)->Unit): RecyclerView.Adapter<StreamAdapter.VH>(){
    class VH(val b: ItemStreamBinding): RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, vt: Int)=VH(ItemStreamBinding.inflate(LayoutInflater.from(p.context),p,false))
    override fun getItemCount()=list.size
    override fun onBindViewHolder(h: VH, pos: Int){
        val s=list[pos]; h.b.tvName.text=s.name; Glide.with(h.b.ivIcon).load(s.icon).into(h.b.ivIcon); h.b.root.setOnClickListener{ onClick(s) }
    }
}
