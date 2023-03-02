package com.rosh.yolharakatiqoidalari.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.rosh.yolharakatiqoidalari.AddActivity
import com.rosh.yolharakatiqoidalari.adapters.RvClick
import com.rosh.yolharakatiqoidalari.adapters.ViewPagerAdapter
import com.rosh.yolharakatiqoidalari.databinding.FragmentHomeBinding
import com.rosh.yolharakatiqoidalari.db.MyDbHelper
import com.rosh.yolharakatiqoidalari.models.Turkum.VIEW_PAGER_ITEM_POSITION
import com.rosh.yolharakatiqoidalari.models.Turkum.WHICH_TYPE_ARRAY
import com.rosh.yolharakatiqoidalari.models.User

class HomeFragment : Fragment(), RvClick {
    private val binding by lazy{ FragmentHomeBinding.inflate(layoutInflater)}
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        myDbHelper = MyDbHelper(requireContext())


        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllUsers(), this)
        binding.myViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.myTabLayout, binding.myViewPager) { tab, position ->
            tab.text = WHICH_TYPE_ARRAY[position]
        }.attach()

        binding.myToolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            intent.putExtra("isEdit", false)
            requireActivity().startActivity(intent)
            true
        }


        return binding.root
    }

    override fun changeLikedState(position: Int, user: User) {
        myDbHelper.editUser(user)
    }

    override fun edit(position: Int, user: User) {
        this.position = position
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("user", user)
        requireActivity().startActivity(intent)
    }

    override fun delete(position: Int, user: User) {
        viewPagerAdapter.deleteUser(position,binding.myViewPager.currentItem)
        myDbHelper.deleteUser(user)
    }

    override fun onStop() {

        VIEW_PAGER_ITEM_POSITION = binding.myViewPager.currentItem

        super.onStop()
    }

    override fun onStart() {
        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllUsers(), this)
        binding.myViewPager.adapter = viewPagerAdapter

        binding.myViewPager.currentItem = VIEW_PAGER_ITEM_POSITION
        super.onStart()
    }


}