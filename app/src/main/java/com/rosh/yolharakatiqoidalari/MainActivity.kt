package com.rosh.yolharakatiqoidalari

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rosh.yolharakatiqoidalari.databinding.ActivityMainBinding
import com.rosh.yolharakatiqoidalari.fragments.Fragment_About
import com.rosh.yolharakatiqoidalari.fragments.HomeFragment
import com.rosh.yolharakatiqoidalari.fragments.LikedFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        actionBar?.hide()
        setContentView(binding.root)


        val about = Fragment_About()
        val likedFragment = LikedFragment()
        val homeFragment = HomeFragment()

        supportFragmentManager.beginTransaction()
            .add(binding.myContainer.id, homeFragment)
            .commit()

        binding.btnNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_menu_about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, about)
                        .commit()
                }
                R.id.bottom_menu_like -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, likedFragment)
                        .commit()
                }
                R.id.bottom_menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, homeFragment)
                        .commit()
                }


            }
            true
        }

    }

    override fun onBackPressed() {
        finish()
    }
}