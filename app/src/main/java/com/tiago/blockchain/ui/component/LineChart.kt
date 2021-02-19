package com.tiago.blockchain.ui.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tiago.blockchain.R
import com.tiago.blockchain.util.formatter.DateAxisFormatter
import com.tiago.blockchain.util.formatter.PriceAxisFormatter

class LineChart(context: Context, attrs: AttributeSet) : LineChart(context, attrs) {

    companion object {
        private const val DURATION_ANIMATION = 750
        private const val MARGIN_TOP = 50f
        private const val NO_OFFSET = 0f
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
            textSize = 10f
            position = XAxis.XAxisPosition.TOP
            valueFormatter = DateAxisFormatter()
        }
    }

    private fun setupYAxis() {
        axisLeft.apply {
            textSize = 12f
            textColor = Color.BLACK
            valueFormatter = PriceAxisFormatter(context.getString(R.string.pattern_price_format))
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        }
        axisRight.apply {
            setDrawLabels(false)
        }
    }

    private fun setupOffset() {
        val metrics = context.resources.displayMetrics
        val offsetTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP, metrics)

        setViewPortOffsets(NO_OFFSET, offsetTop, NO_OFFSET, NO_OFFSET)
    }

    private fun setupLegend() {
        legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }
    }

    fun setData(entries: List<Entry>, label: String) {
        data = LineData(generateLineDataSet(entries, label))
        animateXY(DURATION_ANIMATION, DURATION_ANIMATION)
    }

    private fun generateLineDataSet(entries: List<Entry>, label: String) = LineDataSet(entries, label).apply {
        mode = LineDataSet.Mode.CUBIC_BEZIER
        cubicIntensity = 0.2f
        highLightColor = blackColor
        color = blackColor
        fillColor = blackColor
        lineWidth = 1.8f
        circleRadius = 4f
        setCircleColor(Color.WHITE)
        setDrawFilled(true)
        setDrawCircles(false)
        setDrawHorizontalHighlightIndicator(false)
        setFillFormatter { _, _ -> axisLeft.axisMinimum }
    }

    fun setGridVisibility(visible: Boolean) {
        xAxis.apply {
            setDrawGridLines(visible)
        }
        axisLeft.apply {
            setDrawGridLines(visible)
        }
        axisRight.apply {
            setDrawGridLines(visible)
        }
        invalidate()
    }
}