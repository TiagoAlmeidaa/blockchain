package com.tiago.blockchain.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.FragmentHomeBinding
import com.tiago.blockchain.util.extensions.navigate

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

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