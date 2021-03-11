package com.tiago.blockchain.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tiago.blockchain.BlockChainApplication
import com.tiago.blockchain.R
import com.tiago.blockchain.data.local.BlockChainPreferences
import com.tiago.blockchain.databinding.DialogPeriodBinding
import com.tiago.blockchain.databinding.WidgetRetryBinding
import com.tiago.blockchain.ui.MainActivity
import com.tiago.blockchain.ui.component.ChartToolbar
import com.tiago.blockchain.util.PeriodEnum
import com.tiago.blockchain.util.extensions.*
import com.tiago.blockchain.viewmodel.chart.ChartViewModel
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseChartFragment(layoutId: Int) : Fragment(layoutId) {

    private val blockChainApplication: BlockChainApplication by lazy {
        requireActivity().applicationContext as BlockChainApplication
    }

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    @Inject
    protected lateinit var viewModel: ChartViewModel

    abstract fun getToolbar(): ChartToolbar

    abstract fun getFilterInformationTextView(): AppCompatTextView

    abstract fun getCharView(): View

    abstract fun getRetryLayout(): WidgetRetryBinding

    abstract fun getCircularProgress(): ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setupDagger()
        setupObservers()
        setupUI()

        viewModel.fetchMarketPrices(PeriodEnum.ONE_MONTH)
    }

    private fun setupDagger() {
        blockChainApplication.appComponent.inject(this)
    }

    private fun setupObservers() = with(viewModel) {
        lastPeriod.observe(viewLifecycleOwner, getLastPeriodObserver())
    }

    private fun setupUI() {
        getRetryLayout().apply {
            retryButton.setOnClickListener {
                root.gone()
                viewModel.fetchMarketPrices(viewModel.getLastPeriod())
            }
        }
    }

    private fun getLastPeriodObserver(): Observer<PeriodEnum> = Observer { period ->
        getFilterInformationTextView().text = String.format(
            getString(R.string.pattern_filter_information),
            period.days
        )
    }

    protected fun handleMarketPriceFailed(exception: Throwable) {
        setLoading(false)

        val messageId = if (exception is UnknownHostException) {
            R.string.widget_retry_internet_error_message
        } else {
            R.string.widget_retry_unknown_error_message
        }

        setupRetry(messageId)
    }

    protected fun setLoading(isLoading: Boolean = true) {
        getCircularProgress().setVisibility(isLoading)
        getToolbar().setVisibility(!isLoading)
        getCharView().setVisibility(!isLoading)
        getFilterInformationTextView().setVisibility(!isLoading)
    }

    private fun setupRetry(messageId: Int) {
        getRetryLayout().apply {
            root.visible()
            messageTextView.text = getString(messageId)
        }
        getToolbar().setFilterVisibility(false)
        getToolbar().setGridVisibility(false)
        getCharView().gone()
    }

    protected fun showFilter(parent: ConstraintLayout) {
        DialogPeriodBinding.inflate(layoutInflater, parent, false).apply {
            setSelectedPeriod(viewModel.getLastPeriod())

            val dialog = AlertDialog
                .Builder(requireContext(), R.style.AlertDialog)
                .setView(root)
                .create()

            filterButton.setOnClickListener {
                viewModel.fetchMarketPrices(getSelectedPeriod())
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    protected fun checkShowChartOnBoarding() {
        if (BlockChainPreferences.getShowOnBoarding(requireContext())) {
            BlockChainPreferences.setShowOnBoarding(requireContext(), false)
            mainActivity.showOnBoarding()
        }
    }

}