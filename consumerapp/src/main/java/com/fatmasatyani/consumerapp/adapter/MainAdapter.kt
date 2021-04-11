package com.fatmasatyani.consumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.consumerapp.R
import com.fatmasatyani.consumerapp.data.FavoriteData
import com.fatmasatyani.consumerapp.databinding.ItemUserBinding

class MainAdapter(private val mContext: ArrayList<FavoriteData>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items: MutableList<FavoriteData> = mutableListOf()
    private val glideDimen: Int = 460
    private val message = "Please use main app for the detail features"

    fun setItems(items: MutableList<FavoriteData>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from (parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder (view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(data: FavoriteData) {
            with(itemView) {
                binding.apply {
                    tvUsername.text = data.username
                    Glide.with(itemView.context)
                            .load(data.avatar)
                            .apply(RequestOptions().override(glideDimen, glideDimen))
                            .into(itemView.findViewById(R.id.fv_avatar))
                }
                itemView.setOnClickListener {
//                    Toast.makeText(context, context.getString(R.string.content_provider), Toast.LENGTH_SHORT).show()
                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
//    inner class ViewHolder(private val itemUserBinding: ItemUserBinding): RecyclerView.ViewHolder(itemUserBinding.root) {
//        fun bind (favoriteData: FavoriteData) {
//            itemUserBinding.apply {
//                tvUsername.text = favoriteData.username
//                Glide.with(itemView.context)
//                    .load(favoriteData.avatar)
//                    .apply(RequestOptions().override(glideDimen, glideDimen))
//                    .into(itemUserBinding.fvAvatar)
//                }
////                root.setOnClickListener {
////                }
//
//                Toast.makeText(context,context.getString(R.string.content_provider),Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    inner class GithubViewHolder (private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder (itemUserBinding.root) {
//        fun bind(githubItems: Github) {
//            itemUserBinding.apply {
//                tvUsername.text = githubItems.username
//                Glide.with(itemView.context)
//                    .load(githubItems.avatar)
//                    .apply(RequestOptions().override(glideDimen, glideDimen))
//                    .into(itemUserBinding.civAvatar)
//
//                root.setOnClickListener {
//                    clickListener(githubItems)
//                }
//
//            }
//        }
