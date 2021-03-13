package com.tiago.blockchain.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.FragmentHomeBinding
import com.tiago.blockchain.util.extensions.navigate

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupUI()
    }

    private fun setupUI() = with(binding) {
        lineChartButton.setOnClickListener {
            navigate(R.id.action_homeFragment_to_cubicChartFragment)
        }
        barChartButton.setOnClickListener {
            navigate(R.id.action_homeFragment_to_barChartFragment)
        }
    }
}