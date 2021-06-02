package com.example.anotheruserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anotheruserapp.databinding.ListItemUserBinding

class Adapter : RecyclerView.Adapter<Adapter.UserViewHolder>() {
    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCalback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<User>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(imguser)
                tvusername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}