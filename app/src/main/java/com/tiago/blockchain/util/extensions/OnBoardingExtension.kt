package com.tiago.blockchain.util.extensions

import com.tiago.blockchain.databinding.WidgetOnBoardingHorizontalPinchBinding
import com.tiago.blockchain.databinding.WidgetOnBoardingVerticalPinchBinding

fun WidgetOnBoardingHorizontalPinchBinding.show() = root.visible()

fun WidgetOnBoardingHorizontalPinchBinding.hide() = root.gone()

fun WidgetOnBoardingVerticalPinchBinding.show() = root.visible()

fun WidgetOnBoardingVerticalPinchBinding.hide() = root.gone()