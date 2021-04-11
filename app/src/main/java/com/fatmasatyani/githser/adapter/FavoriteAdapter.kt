package com.fatmasatyani.githser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.githser.databinding.ItemUserBinding
import com.fatmasatyani.githser.entity.FavoriteData

class FavoriteAdapter (private val listener: (FavoriteData)->Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private val glideDimen: Int = 60
    private var cities = listOf<FavoriteData>()
    fun set(cities: List<FavoriteData>) {
        this.cities = cities

        notifyDataSetChanged()
    }

    inner class ViewHolder (private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root) {
        fun bind(user: FavoriteData) {
            itemUserBinding.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(glideDimen, glideDimen))
                    .into(itemUserBinding.civAvatar)
                userItem.setOnClickListener {
                    listener(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size
}