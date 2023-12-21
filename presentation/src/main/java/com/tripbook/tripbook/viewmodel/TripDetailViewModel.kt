package com.tripbook.tripbook.viewmodel

import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.usecase.ArticleDetailUseCase
import com.tripbook.tripbook.domain.usecase.MemberUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    fun loadArticleDetailInfo(webView: WebView) {
        viewModelScope.launch {
            articleDetail.collect { detail ->
                detail?.let {
                    val content = it.content

                    if (!content.isNullOrBlank()) {
                        webView.visibility = View.VISIBLE
                        webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
                    }
                }
            }
        }
    }


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