package com.rosh.yolharakatiqoidalari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosh.yolharakatiqoidalari.databinding.ItemViewPagerBinding
import com.rosh.yolharakatiqoidalari.models.User

class ViewPagerAdapter(
    val context: Context,
    val list: ArrayList<User>,
    val rvClickInterFace: RvClick
) : RecyclerView.Adapter<ViewPagerAdapter.Vh>() {

    lateinit var rvAdapter0: RvAdapter
    lateinit var rvAdapter: RvAdapter
    lateinit var rvAdapter1: RvAdapter
    lateinit var rvAdapter2: RvAdapter
    lateinit var rvAdapter3: RvAdapter

    inner class Vh(private val itemRvBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(position: Int) {
            val tempList = ArrayList<User>()
            for (it in list) {
                if (position == it.whichType!!) {
                    tempList.add(it)
                }
            }

            rvAdapter = RvAdapter(context, tempList, rvClickInterFace)
            itemRvBinding.myRv.adapter = rvAdapter

            when (position) {
                0 -> {
                    rvAdapter0 = RvAdapter(context, tempList, rvClickInterFace)
                    itemRvBinding.myRv.adapter = rvAdapter0

                }
                1 -> {
                    rvAdapter1 = RvAdapter(context, tempList, rvClickInterFace)
                    itemRvBinding.myRv.adapter = rvAdapter1

                }
                2 -> {
                    rvAdapter2 = RvAdapter(context, tempList, rvClickInterFace)
                    itemRvBinding.myRv.adapter = rvAdapter2

                }
                3 -> {
                    rvAdapter3 = RvAdapter(context, tempList, rvClickInterFace)
                    itemRvBinding.myRv.adapter = rvAdapter3

                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind(position)


    override fun getItemCount(): Int = 4

    fun deleteUser(position: Int, currentItem: Int) {

        when (currentItem) {
            0 -> {
                rvAdapter0.list.removeAt(position)
                rvAdapter0.notifyItemRemoved(position)
                rvAdapter0.notifyItemRangeChanged(0, rvAdapter0.itemCount)
            }
            1 -> {
                rvAdapter1.list.removeAt(position)
                rvAdapter1.notifyItemRemoved(position)
                rvAdapter1.notifyItemRangeChanged(0, rvAdapter1.itemCount)
            }
            2 -> {
                rvAdapter2.list.removeAt(position)
                rvAdapter2.notifyItemRemoved(position)
                rvAdapter2.notifyItemRangeChanged(0, rvAdapter2.itemCount)
            }
            3 -> {
                rvAdapter3.list.removeAt(position)
                rvAdapter3.notifyItemRemoved(position)
                rvAdapter3.notifyItemRangeChanged(0, rvAdapter3.itemCount)
            }

        }


    }
}

