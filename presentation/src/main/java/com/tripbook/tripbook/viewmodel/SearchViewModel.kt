package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.usecase.ClearItemUseCase
import com.tripbook.tripbook.domain.usecase.DeleteSearchUseCase
import com.tripbook.tripbook.domain.usecase.GetCurrentKeywordUseCase
import com.tripbook.tripbook.domain.usecase.SearchItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clearItemUseCase: ClearItemUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val searchItemUseCase: SearchItemUseCase,
    currentKeywordUseCase: GetCurrentKeywordUseCase
) : BaseViewModel() {

    val editableKeyword = MutableStateFlow("")

    private val _currentKeyword: MutableStateFlow<String> = MutableStateFlow("")
    val currentKeyword: StateFlow<String>
        get() = _currentKeyword

    val keywordList = currentKeywordUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val searchedItem = currentKeyword.filter { it.isNotEmpty() }.flatMapConcat {
        searchItemUseCase(it)
    }.filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )


    fun clearEditable() = viewModelScope.launch {
        editableKeyword.emit("")
    }

    fun clearItem() = clearItemUseCase().launchIn(viewModelScope)

    fun clickRecentKeyword(keyword: String) = viewModelScope.launch {
        editableKeyword.emit(keyword).also {
            searchKeyword()
        }
    }

    fun deleteSearchKeyword(keyword: String) = deleteSearchUseCase(keyword).launchIn(viewModelScope)

    fun searchKeyword() = viewModelScope.launch {
        if (editableKeyword.value.isNotEmpty()) {
            _currentKeyword.emit(editableKeyword.value)
        }
    }

}