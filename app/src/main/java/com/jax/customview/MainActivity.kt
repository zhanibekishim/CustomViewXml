package com.jax.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jax.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val menus = listOf("sex","life","money","skill","brain","strength")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.arcView.onArcClickListener = {index->
            binding.tvInfo.text = "Your power is: "+menus[index]
        }
    }
}