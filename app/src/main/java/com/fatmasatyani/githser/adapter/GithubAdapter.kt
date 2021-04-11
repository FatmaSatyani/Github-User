package com.fatmasatyani.githser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatmasatyani.githser.databinding.ItemUserBinding
import com.fatmasatyani.githser.entity.Github

class GithubAdapter(private var list: ArrayList<Github>, private val clickListener: (Github) -> Unit) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {

    private val listGithub = ArrayList<Github> ()
    private var onItemClickCallback : OnItemClickCallback? = null
    private val glideDimen: Int = 460
    private var cities = listOf<Github>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData (filterList: ArrayList<Github>) {
        this.listGithub.clear()
        this.listGithub.addAll(filterList)
        notifyDataSetChanged()
    }

    fun set(lists: List<Github>) {
        this.cities = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val itemUserBinding= ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return GithubViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(listGithub[position])
    }

    override fun getItemCount(): Int = listGithub.size



    inner class GithubViewHolder (private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder (itemUserBinding.root) {
        fun bind(githubItems: Github) {
            itemUserBinding.apply {
                tvUsername.text = githubItems.username
                Glide.with(itemView.context)
                    .load(githubItems.avatar)
                    .apply(RequestOptions().override(glideDimen, glideDimen))
                    .into(itemUserBinding.civAvatar)

                root.setOnClickListener {
                    clickListener(githubItems)
                }

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Github)
    }
}
