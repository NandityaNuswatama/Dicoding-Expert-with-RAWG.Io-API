package com.nandits.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nandits.core.databinding.ItemRvBinding
import com.nandits.core.domain.model.Game

class GameAdapter: RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    private var listData = ArrayList<Game>()
    var onItemClick: ((Game) -> Unit)?= null
    
    fun setData(data: List<Game>?){
        if (data == null) return
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }
    
    inner class GameViewHolder(private val binding: ItemRvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Game){
            with(binding){
                imgBackground.load(data.image){
                    crossfade(true)
                    crossfade(300)
                }
                tvName.text = data.name
                tvMeta.text = data.metaCritic.toString()
                tvRating.text = data.rating.toString()
                if (data.isFavorite){
                    imgFav.isGone = false
                }
                root.setOnClickListener {
                    onItemClick?.invoke(listData[adapterPosition])
                }
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        return holder.bind(listData[position])
    }
    
    override fun getItemCount(): Int {
        return listData.size
    }
}