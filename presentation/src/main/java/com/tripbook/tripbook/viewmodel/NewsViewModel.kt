package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("UNUSED")
class NewsViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : BaseViewModel() {

    private val _articleList: MutableStateFlow<List<ArticleDetail>> = MutableStateFlow(emptyList())
    val articleList: StateFlow<List<ArticleDetail>>
        get() = _articleList

    private val keyword: MutableStateFlow<String> = MutableStateFlow("") // USE in XML
    private val searchedData: StateFlow<String>
        get() = keyword.debounce(800)
            .distinctUntilChanged()
            .onEach {
                getFilteredItem()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ""
            )

    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.CREATED_DESC)
    val sortType: StateFlow<SortType>
        get() = _sortType

    init {
        getFilteredItem()
    }

    private fun getFilteredItem() {
        viewModelScope.launch {
            getArticleUseCase(searchedData.value, sortType.value)
                .onEach {
                    _articleList.emit(it?.content ?: emptyList())
                }.collect()
        }
    }
}