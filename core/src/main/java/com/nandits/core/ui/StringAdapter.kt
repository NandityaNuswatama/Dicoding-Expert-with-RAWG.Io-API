package com.nandits.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nandits.core.databinding.ItemPlatformBinding

class StringAdapter: RecyclerView.Adapter<StringAdapter.StringViewHolder>() {
    private var listData = ArrayList<String>()
    
    fun setData(data: List<String>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }
    
    inner class StringViewHolder(private val binding: ItemPlatformBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String){
            binding.tvPlatform.text = data
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val view = ItemPlatformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        return holder.bind(listData[position])
    }
    
    override fun getItemCount(): Int {
        return listData.size
    }
}