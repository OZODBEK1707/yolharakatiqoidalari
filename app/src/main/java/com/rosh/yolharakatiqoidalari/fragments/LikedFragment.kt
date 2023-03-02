package com.rosh.yolharakatiqoidalari.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rosh.yolharakatiqoidalari.AddActivity
import com.rosh.yolharakatiqoidalari.adapters.RvAdapter
import com.rosh.yolharakatiqoidalari.adapters.RvClick
import com.rosh.yolharakatiqoidalari.databinding.FragmentLikedBinding
import com.rosh.yolharakatiqoidalari.db.MyDbHelper
import com.rosh.yolharakatiqoidalari.models.Turkum
import com.rosh.yolharakatiqoidalari.models.Turkum.TEMP_USER
import com.rosh.yolharakatiqoidalari.models.User
import java.io.File


class LikedFragment : Fragment(), RvClick {
    private val binding by lazy { FragmentLikedBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())
        rvAdapter = RvAdapter(requireContext(), myDbHelper.getLikedUsers(), this)

        binding.myRv.adapter = rvAdapter

        return binding.root
    }

    override fun changeLikedState(position: Int, user: User) {
        myDbHelper.editUser(user)
        rvAdapter.list.removeAt(position)
        rvAdapter.notifyItemRemoved(position)
    }

    override fun edit(position: Int, user: User) {
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("user", user)
        requireActivity().startActivity(intent)
        this.position = position
    }

    override fun delete(position: Int, user: User) {
        myDbHelper.deleteUser(user)
        rvAdapter.list.removeAt(position)
        rvAdapter.notifyItemRemoved(position)
        rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)

        val file = File(requireActivity().filesDir, "${user.id}.jpg")
        file.delete()
    }
    override fun onStart() {
        if (Turkum.USER_EDITED_STATE) {
            Turkum.USER_EDITED_STATE = false
            rvAdapter.list[position] = TEMP_USER
            rvAdapter.notifyItemChanged(position)
        }

        super.onStart()
    }


}