package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.usecase.ArticleDetailUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Suppress("UNCHECKED_CAST")
class TripDetailViewModel @AssistedInject constructor(
    useCase: ArticleDetailUseCase,
    @Assisted private val articleId: Long
): BaseViewModel() {

    val articleDetail = useCase(articleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @AssistedFactory
    interface DetailAssistedFactory {
        fun create(articleId: Long): TripDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DetailAssistedFactory,
            articleId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(articleId) as T
            }
        }
    }
}