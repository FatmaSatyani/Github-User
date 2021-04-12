package com.fatmasatyani.githser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.githser.databinding.ItemUserBinding
import com.fatmasatyani.githser.entity.Github

class HomeAdapter (private val listener: (Github) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val glideDimen: Int = 460
    private var lists = listOf<Github>()

    fun set(lists: List<Github>) {
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(view.context),view,false)
        return ViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount(): Int = lists.size

    inner class ViewHolder (private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root) {
        fun bind(user: Github) {
            itemUserBinding.apply {
                tvUsername.text = user.username
                Glide.with(itemUserBinding.civAvatar)
                    .load(user.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions().override(glideDimen, glideDimen))
                    .into(civAvatar)
                userItem.setOnClickListener {
                    listener(user)
                }
            }
        }
    }
}