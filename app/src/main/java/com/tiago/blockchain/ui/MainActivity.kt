package com.tiago.blockchain.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tiago.blockchain.databinding.ActivityMainBinding
import com.tiago.blockchain.util.extensions.hide
import com.tiago.blockchain.util.extensions.show

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupOnBoardingVertical()
        setupOnBoardingHorizontal()
    }

    private fun setupOnBoardingVertical() = with(binding.onBoardingVertical) {
        okButton.setOnClickListener {
            hide()
            binding.onBoardingHorizontal.show()
        }
    }

    private fun setupOnBoardingHorizontal() = with(binding.onBoardingHorizontal) {
        gotItButton.setOnClickListener {
            hide()
        }
    }

    fun showOnBoarding() = binding.onBoardingVertical.show()

}