package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("UNUSED")
class NewsViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : BaseViewModel() {

    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.CREATED_DESC)
    private val sortType: StateFlow<SortType>
        get() = _sortType

    private val _openSpinner: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val openSpinner: StateFlow<Boolean>
        get() = _openSpinner

    val articleList = sortType.flatMapLatest {
        getArticleUseCase("", it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    ).cachedIn(viewModelScope)

    fun setSortType(sortType: SortType) {
        viewModelScope.launch {
            _sortType.emit(sortType)
        }
    }
}