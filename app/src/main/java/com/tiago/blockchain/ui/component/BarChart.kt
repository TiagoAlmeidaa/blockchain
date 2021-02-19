package com.tiago.blockchain.ui.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.tiago.blockchain.R
import com.tiago.blockchain.util.formatter.DateAxisFormatter
import com.tiago.blockchain.util.formatter.PriceAxisFormatter

class BarChart(context: Context, attrs: AttributeSet) : BarChart(context, attrs) {

    companion object {
        private const val DURATION_ANIMATION = 750
        private const val MARGIN_TOP = 40f
    }

    private val blackColor: Int by lazy {
        ContextCompat.getColor(context, android.R.color.black)
    }

    private val whiteColor: Int by lazy {
        ContextCompat.getColor(context, android.R.color.white)
    }

    init {
        setBackgroundColor(whiteColor)

        setupDescription()
        setupXAxis()
        setupYAxis()
        setupOffset()
        setupLegend()
    }

    private fun setupDescription() {
        description = Description().apply {
            text = ""
            isEnabled = false
        }
    }

    private fun setupXAxis() {
        xAxis.apply {
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.setDrawGridLines(false)
            textSize = 10f
            valueFormatter = DateAxisFormatter()
        }
    }

    private fun setupYAxis() {
        axisLeft.apply {
            textSize = 10f
            textColor = Color.BLACK
            valueFormatter = PriceAxisFormatter(context.getString(R.string.pattern_price_format))
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        }
        axisRight.apply {
            textSize = 10f
            textColor = Color.BLACK
            valueFormatter = PriceAxisFormatter(context.getString(R.string.pattern_price_format))
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        }
    }

    private fun setupOffset() {
        val metrics = context.resources.displayMetrics
        val offset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP, metrics)

        setViewPortOffsets(offset, offset, offset, offset)
    }

    private fun setupLegend() {
        legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }
    }

    fun setData(entries: List<BarEntry>, label: String) {
        val barDataSet = BarDataSet(entries, label).apply {
            barBorderColor = blackColor
            highLightColor = blackColor
            color = blackColor
            barBorderWidth = 2f
        }
        data = BarData(barDataSet).apply {
            setValueTextSize(10f)
        }
        animateXY(DURATION_ANIMATION, DURATION_ANIMATION)
    }

}