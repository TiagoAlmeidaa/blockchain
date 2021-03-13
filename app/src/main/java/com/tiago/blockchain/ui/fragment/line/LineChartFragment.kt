package com.tiago.blockchain.ui.fragment.line

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.FragmentLineChartBinding
import com.tiago.blockchain.databinding.WidgetRetryBinding
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.ui.component.ChartToolbar
import com.tiago.blockchain.ui.fragment.BaseChartFragment

class LineChartFragment : BaseChartFragment(R.layout.fragment_line_chart) {

    private val binding by viewBinding(FragmentLineChartBinding::bind)

    override fun getToolbar(): ChartToolbar = binding.toolbar

    override fun getFilterInformationTextView(): AppCompatTextView = binding.filterInformationTextView

    override fun getCharView(): View = binding.lineChart

    override fun getRetryLayout(): WidgetRetryBinding = binding.retry

    override fun getCircularProgress(): ProgressBar = binding.circularProgress

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupObservers()
        setupUI()
    }

    private fun setupObservers() = with(viewModel) {
        state.observe(viewLifecycleOwner, getStateObserver())
    }

    private fun setupUI() = with(binding) {
        toolbar.apply {
            setup(R.string.line_chart_fragment_title) { findNavController().popBackStack() }
            setupFilterOption { showFilter(binding.root) }
            setupGridOption { visible -> lineChart.setGridVisibility(visible) }
        }
    }

    private fun getStateObserver(): Observer<BlockChainState> = Observer { state ->
        when (state) {
            is BlockChainState.OnMarketPriceReceived -> handleMarketPriceReceived(state.marketPrice)
            is BlockChainState.OnMarketPriceFetchFailed -> handleMarketPriceFailed(state.exception)
            is BlockChainState.OnLoading -> setLoading()
        }
    }

    private fun handleMarketPriceReceived(marketPrice: MarketPrice) = with(binding) {
        checkShowChartOnBoarding()
        setLoading(false)
        toolbar.setFilterVisibility(true)
        toolbar.setGridVisibility(true)
        lineChart.setData(marketPrice.convertPricesToEntry(), marketPrice.name)
    }

}