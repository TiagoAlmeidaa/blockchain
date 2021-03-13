package com.tiago.blockchain.ui.fragment.bar

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.FragmentBarChartBinding
import com.tiago.blockchain.databinding.WidgetRetryBinding
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.ui.component.ChartToolbar
import com.tiago.blockchain.ui.fragment.BaseChartFragment

class BarChartFragment : BaseChartFragment(R.layout.fragment_bar_chart) {

    private val binding by viewBinding(FragmentBarChartBinding::bind)

    override fun getToolbar(): ChartToolbar = binding.toolbar

    override fun getFilterInformationTextView(): AppCompatTextView = binding.filterInformationTextView

    override fun getCharView(): View = binding.barChart

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
            setup(R.string.bar_chart_fragment_title) { findNavController().popBackStack() }
            setupFilterOption { showFilter(binding.root) }
        }
    }

    private fun getStateObserver(): Observer<BlockChainState> = Observer { state ->
        when (state) {
            is BlockChainState.OnMarketPriceReceived -> handleMarketPriceReceived(state.marketPrice)
            is BlockChainState.OnMarketPriceFetchFailed -> handleMarketPriceFailed(state.exception)
            is BlockChainState.OnLoading -> setLoading()
            is BlockChainState.InvalidResponse -> setupRetry(R.string.widget_retry_invalid_response_error_message)
        }
    }

    private fun handleMarketPriceReceived(marketPrice: MarketPrice) = with(binding) {
        checkShowChartOnBoarding()
        setLoading(false)
        toolbar.setFilterVisibility(true)
        barChart.setData(marketPrice.convertPricesToBarEntry(), marketPrice.name)
    }

}