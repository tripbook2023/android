package com.tripbook.tripbook

import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class TripBookBaseViewModel: BaseViewModel() {
    private val _errorFlow: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val errorFlow: StateFlow<Throwable?>
        get() = _errorFlow

    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _errorFlow.value = throwable
        }
    }
    protected val baseCoroutineContext = viewModelScope.plus(exceptionHandler)
}