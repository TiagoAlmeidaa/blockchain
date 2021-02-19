package com.tiago.blockchain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun runWithCoroutines(
        doInBackground: suspend () -> Unit,
        doOnError: (Exception) -> Unit
    ) = viewModelScope.launch {
        try {
            doInBackground()
        } catch (exception: Exception) {
            doOnError(exception)
        }
    }
}