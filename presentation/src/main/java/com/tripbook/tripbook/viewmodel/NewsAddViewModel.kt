package com.tripbook.tripbook.viewmodel

import android.webkit.JavascriptInterface
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.model.TempArticle
import com.tripbook.tripbook.domain.usecase.DeleteArticleUseCase
import com.tripbook.tripbook.domain.usecase.GetLocationUseCase
import com.tripbook.tripbook.domain.usecase.GetTempArticleUseCase
import com.tripbook.tripbook.domain.usecase.SaveArticleUseCase
import com.tripbook.tripbook.domain.usecase.SaveTempArticleUseCase
import com.tripbook.tripbook.domain.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
@Suppress("UNUSED")
class NewsAddViewModel @Inject constructor(
    private val saveArticleUseCase: SaveArticleUseCase,
    private val locationUseCase: GetLocationUseCase,
    private val tempListUseCase: GetTempArticleUseCase,
    private val saveTempArticleUseCase: SaveTempArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : BaseViewModel() {

    private val _tempId = MutableStateFlow<Long?>(null)

    private val _contentLength = MutableStateFlow(0)
    val contentLength: StateFlow<Int> get() = _contentLength

    private val _titleLength = MutableStateFlow(0)
    val titleLength: StateFlow<Int> get() = _titleLength

    private val _tempSaveCount = MutableStateFlow(0) // 임시저장글 개수
    val tempSaveCount: StateFlow<Int> get() = _tempSaveCount

    private val _basicOptions = MutableStateFlow(true)
    val basicOptions: StateFlow<Boolean> = _basicOptions

    private val _thumbnailUrl = MutableStateFlow<String?>(null)
    val thumbNailUrl: StateFlow<String?> = _thumbnailUrl

    private val _thumbnailId = MutableStateFlow<Int?>(0)
    val thumbnailId: StateFlow<Int?> = _thumbnailId

    private val _textOptionsTitle = MutableStateFlow(false)
    val textOptionsTitle: StateFlow<Boolean> get() = _textOptionsTitle

    private val _textOptionsSubtitle = MutableStateFlow(false)
    val textOptionsSubtitle: StateFlow<Boolean> get() = _textOptionsSubtitle

    private val _textOptionsMainText = MutableStateFlow(false)
    val textOptionsMainText: StateFlow<Boolean> get() = _textOptionsMainText

    private val _textOptionsBold = MutableStateFlow(false)
    val textOptionsBold: StateFlow<Boolean> get() = _textOptionsBold

    private val _imageList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val imageList: StateFlow<List<Int>> get() = _imageList

    private val _uiStatus: MutableStateFlow<UiStatus> = MutableStateFlow(UiStatus.IDLE)
    val uiStatus: StateFlow<UiStatus> get() = _uiStatus

    // 여행소식 등록 조건
    // 표지 이미지 등록, 표지 제목 등록, 최소 50자 이상, 이미지 1장 이상
    val allConditionSatisfied: StateFlow<Boolean> = combine(
        _thumbnailUrl, _titleLength, _contentLength, _imageList
    ) { thumbnail, titleLength, contentLength, imageList ->
        thumbnail != null && titleLength != 0 && contentLength >= 50 && imageList.size >= 1
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    // 임시저장 리스트 관련
    private val _tempSaveListIndex = MutableStateFlow(-1)
    val tempSaveListIndex: StateFlow<Int> get() = _tempSaveListIndex

    private val _tempSaveList = MutableStateFlow<List<TempArticle>>(listOf())
    val tempSaveList: StateFlow<List<TempArticle>> get() = _tempSaveList

    // 위치 검색 리스트 관련
    private val _locationListIndex = MutableStateFlow(-1)
    val locationListIndex: StateFlow<Int> get() = _locationListIndex

    private val _locationList = MutableStateFlow<MutableList<Location>>(mutableListOf())

    fun setLocationList(list: MutableList<Location>){
        _locationList.value = list
    }

    private val _locationAdd = MutableStateFlow<Location?>(null)
    val locationAdd: StateFlow<Location?> = _locationAdd

    private val _locationSearchList = MutableStateFlow<List<Location>>(listOf())
    val locationSearchList: StateFlow<List<Location>> get() = _locationSearchList

    fun addLocationTag(location: Location) {
        _locationList.value.add(location)
    }

    fun addImage(id: Int) {
        _imageList.value.add(id)
    }

    fun searchLocation(location: String) {
        viewModelScope.launch {
            locationUseCase(location).collect {
                if (it.isNullOrEmpty().not())
                    _locationSearchList.value = it as List<Location>
            }
        }
    }

    fun getTempList(){
        viewModelScope.launch{
            tempListUseCase.invoke().collect{ tempList ->
                if(tempList.isNullOrEmpty()){
                    _tempSaveCount.value = 0
                }else{
                    _tempSaveCount.value = tempList.size
                    _tempSaveList.value = tempList
                }
            }
        }
    }

    fun setUiStatus(status: UiStatus) {
        _uiStatus.value = status
    }

    fun setLocation(location: Location?) {
        _locationAdd.value = location
    }

    fun setLocationListIndex(index: Int) {
        _locationListIndex.value = index
    }

    fun setTempSaveListIndex(index: Int) {
        _tempSaveListIndex.value = index
    }

    fun setThumbnailId(id: Int) {
        _thumbnailId.value = id
    }

    fun initLocationList() {
        _locationSearchList.value = listOf()
    }

    fun setTextOptions(option: TextView, checked: Boolean?) {
        if (checked != null) {
            when (option.id) {
                R.id.text_options_title -> {
                    _textOptionsTitle.value = checked
                    if (checked) {
                        _textOptionsSubtitle.value = false
                        _textOptionsMainText.value = false
                    }
                }

                R.id.text_options_subtitle -> {
                    _textOptionsSubtitle.value = checked
                    if (checked) {
                        _textOptionsTitle.value = false
                        _textOptionsMainText.value = false
                    }
                }

                R.id.text_options_main_text -> {
                    _textOptionsMainText.value = checked
                    if (checked) {
                        _textOptionsTitle.value = false
                        _textOptionsSubtitle.value = false
                    }
                }

                R.id.text_options_bold -> {
                    _textOptionsBold.value = checked
                }
            }
        } else {
            _textOptionsBold.value = _textOptionsBold.value.not()
        }
    }

    fun setBasicOptions() {
        _basicOptions.value = _basicOptions.value.not()
    }

    fun setContentLength(len: Int) {
        _contentLength.value = len
    }

    fun setTitleLength(len: Int) {
        _titleLength.value = len
    }

    fun setThumbnail(url: String?) {
        _thumbnailUrl.value = url
    }

    fun setTempId(id: Long){
        _tempId.value = id
    }

    fun saveTripNews(title: String, content: String): Flow<Long?> {
        _thumbnailId.value?.let { addImage(it) }
        return saveArticleUseCase(
            _tempId.value,
            title,
            content,
            _thumbnailUrl.value!!,
            _imageList.value,
            _locationList.value
        )
    }

    fun saveTempArticle(title: String, content: String): Flow<Long?>  {
        _thumbnailId.value?.let { addImage(it) }
        return saveTempArticleUseCase(
            _tempId.value, // 임시저장했던 article을 다시 임시저장할 때 pk 첨부
            title,
            content,
            _thumbnailUrl.value,
            _imageList.value,
            _locationList.value
        )
    }

    fun deleteArticle(id: Long){
        viewModelScope.launch{
            deleteArticleUseCase(id).collect{
                if(it) getTempList()
            }
        }
    }

    fun uploadImage(file: File) = uploadImageUseCase(file)

    inner class JavaInterface {
        @JavascriptInterface
        fun removeImageItem(id: Int) {
            _imageList.value.remove(id)
        }

        @JavascriptInterface
        fun removeTagItem(tag: String) {
            for(location in _locationList.value){
                if(location.place_name == tag)
                    _locationList.value.removeAt(_locationList.value.indexOf(location))
            }
        }
    }

    enum class UiStatus {
        IDLE,
        TITLE_GALLERY,
        CONTENT_GALLERY,
        LOCATION,
        TEMP_SAVE,
        TEMP_SAVE_SUCCESS,
        SELECT_TEMP,
        DELETE_TEMP,
        HIDE_KEYBOARD,
        TITLE,
        SUBTITLE,
        MAIN_TEXT,
        BOLD,
        NEWS_ADD
    }

}
