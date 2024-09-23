package com.jax.customview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jax.customview.databinding.ActivityMainBinding
import com.jax.customview.utils.MenuList.menus

class MainActivity : AppCompatActivity(){

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.arcView.onArcClickListener = {index->
            binding.tvInfo.text = menus[index]
        }
    }
}