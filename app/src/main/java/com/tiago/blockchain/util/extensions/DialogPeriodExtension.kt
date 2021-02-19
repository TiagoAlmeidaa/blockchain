package com.tiago.blockchain.util.extensions

import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.DialogPeriodBinding
import com.tiago.blockchain.util.PeriodEnum

fun DialogPeriodBinding.getSelectedPeriod(): PeriodEnum =
    when (filterRadioGroup.checkedRadioButtonId) {
        R.id.one_week_radio_button -> PeriodEnum.ONE_WEEK
        R.id.one_year_radio_button -> PeriodEnum.ONE_YEAR
        else -> PeriodEnum.ONE_MONTH
    }

fun DialogPeriodBinding.setSelectedPeriod(periodEnum: PeriodEnum) {
    val id = when(periodEnum) {
        PeriodEnum.ONE_WEEK -> R.id.one_week_radio_button
        PeriodEnum.ONE_MONTH -> R.id.one_month_radio_button
        PeriodEnum.ONE_YEAR -> R.id.one_year_radio_button
    }
    filterRadioGroup.check(id)
}