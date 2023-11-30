package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.usecase.ArticleDetailUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("UNCHECKED_CAST")
class TripDetailViewModel @AssistedInject constructor(
    private val useCase: ArticleDetailUseCase,
    @Assisted private val articleId: Long
): BaseViewModel() {

    fun getArticleDetail(articleId: Long) = useCase(articleId)

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