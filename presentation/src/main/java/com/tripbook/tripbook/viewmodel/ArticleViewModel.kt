package com.tripbook.tripbook.viewmodel


import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.usecase.ArticleDetailUseCase
import com.tripbook.tripbook.domain.usecase.ArticleLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleDetailUseCase: ArticleDetailUseCase,
    private val articleLikeUseCase : ArticleLikeUseCase
) : BaseViewModel() {

    //여행소식 상세보기
    private val _articleDetailInfo: MutableStateFlow<ArticleDetail?> = MutableStateFlow(null)
    val articleDetailInfo: MutableStateFlow<ArticleDetail?> = _articleDetailInfo

/*    private val _imageList = MutableStateFlow<List<Image>>(emptyList())
    val imageList: MutableStateFlow<List<Image>> get() = _imageList*/

    //프로필 사진
    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    //닉네임(글쓴이)
    private val _nickname: MutableStateFlow<String?> = MutableStateFlow(null)
    val nickname: StateFlow<String?> = _nickname

    //썸네일
    private val _thumbnailUrl = MutableStateFlow<String?>(null)
    val thumbnailUrl: StateFlow<String?> = _thumbnailUrl

    //큰제목
    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: StateFlow<String?> = _title

    //내용
    private val _content: MutableStateFlow<String?> = MutableStateFlow(null)
    val content: StateFlow<String?> = _content

    //좋아요 수
    private val _heartNum: MutableStateFlow<Long?> = MutableStateFlow(null)
    val heartNum: StateFlow<Long?> = _heartNum

    fun likeArticle(articleId : Long) = articleLikeUseCase(articleId).onEach {

    }.launchIn(viewModelScope)

        fun getArticleDetail(articleId : Long) = articleDetailUseCase(articleId).onEach {
        _articleDetailInfo.emit(it)

        it?.run {
            //프로필 사진
            _profileUri.emit(Uri.parse(author?.profileUrl))

            //글쓴이 닉네임
            _nickname.value = it.author?.name

            //썸네일
            _thumbnailUrl.emit(it.thumbnailUrl)

            //큰제목
            _title.value = it?.title

            //내용
            _content.value = it?.content

            //좋아요 수
            _heartNum.value = it?.numberOfHearts

        }

    }.launchIn(viewModelScope)

    fun setHeartNum(heart: Long) {
        _heartNum.value = heart
    }


}