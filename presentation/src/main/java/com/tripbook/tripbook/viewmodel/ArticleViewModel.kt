package com.tripbook.tripbook.viewmodel


import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.usecase.ArticleLikeUseCase
import com.tripbook.tripbook.domain.usecase.ArticleReportUseCase
import com.tripbook.tripbook.domain.usecase.MemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleLikeUseCase : ArticleLikeUseCase,
    private val memberUseCase: MemberUseCase,
    private val articleReportUseCase: ArticleReportUseCase
) : BaseViewModel() {

    //좋아요 수
    private val _heartNum: MutableStateFlow<Long?> = MutableStateFlow(null)
    val heartNum: StateFlow<Long?> = _heartNum

    private val _userName: MutableStateFlow<String?> = MutableStateFlow(null)
    val userName: StateFlow<String?> = _userName

    private var _heart = MutableStateFlow(false)
    val heart: StateFlow<Boolean> get() = _heart

    private var _author = MutableStateFlow(false)
    val author: StateFlow<Boolean> get() = _author

    private var _articleId = MutableStateFlow<Long>(0)

    private var _reportComplete = MutableStateFlow(false)
    val reportComplete: StateFlow<Boolean> get() = _reportComplete

    fun setReportResult(result: Boolean){
        _reportComplete.value = result
    }
    fun setArticleId(articleId: Long){
        _articleId.value = articleId
    }
    fun reportArticle(content: String) = articleReportUseCase(_articleId.value, content)
    fun likeArticle(articleId : Long) = articleLikeUseCase(articleId).onEach {

        if (it != null) {
            _heart.value = it.heart
            _heartNum.value = it.heartNum

        }
    }.launchIn(viewModelScope)

    fun getUserList(){
        viewModelScope.launch{
            memberUseCase.invoke().collect{
                _userName.value = it?.name
            }
        }
    }

    fun setHeartNum(heart: Long) {
        _heartNum.value = heart
    }

    fun setHeart(heart: Boolean) {
        _heart.value = heart
    }

    fun authorChk(author: String) {
         _author.value = author == _userName.value
    }

}