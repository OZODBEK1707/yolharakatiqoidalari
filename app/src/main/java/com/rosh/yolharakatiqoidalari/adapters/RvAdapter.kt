package com.rosh.yolharakatiqoidalari.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosh.yolharakatiqoidalari.R
import com.rosh.yolharakatiqoidalari.databinding.ItemRvBinding
import com.rosh.yolharakatiqoidalari.models.User

class RvAdapter(val context: Context, var list: ArrayList<User>, val rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User, position: Int) {
            itemRvBinding.tvName.text = user.name
            itemRvBinding.image.setImageURI(Uri.parse(user.photoPath))
            if (user.likedState == 1) {
                itemRvBinding.btnLike.setImageResource(R.drawable.full_heart)
            }

            itemRvBinding.apply {
                btnLike.setOnClickListener {
                    if (user.likedState == 0) {
                        btnLike.setImageResource(R.drawable.full_heart)
                        list[position].likedState = 1
                        user.likedState = 1
                        rvClick.changeLikedState(position, user)
                    } else {
                        btnLike.setImageResource(R.drawable.empty_heart)
                        list[position].likedState = 0
                        user.likedState = 0
                        rvClick.changeLikedState(position, user)
                    }
                }

                btnDelete.setOnClickListener {
                    rvClick.delete(position, user)
                }

                btnEdit.setOnClickListener {
                    rvClick.edit(position, user)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RvAdapter.Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}

interface RvClick {
    fun changeLikedState(position: Int, user: User)
    fun edit(position: Int,user: User)
    fun delete(position: Int, user: User)
}
