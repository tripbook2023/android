package com.tripbook.tripbook.viewmodel

import android.content.Context
import android.net.Uri
import android.webkit.JavascriptInterface
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import com.tripbook.tripbook.Utils.getImagePathFromURI
import com.tripbook.tripbook.domain.model.Location
import com.tripbook.tripbook.domain.usecase.GetLocationUseCase
import com.tripbook.tripbook.domain.usecase.SaveTripNewsUseCase
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
class NewsAddViewModel @Inject constructor(
    private val saveTripNewsUseCase: SaveTripNewsUseCase,
    private val locationUseCase: GetLocationUseCase
) : BaseViewModel() {

    private val _contentLength = MutableStateFlow(0)
    val contentLength: StateFlow<Int> get() = _contentLength

    private val _titleLength = MutableStateFlow(0)
    val titleLength: StateFlow<Int> get() = _titleLength

    private val _tempSaveCount = MutableStateFlow(0) // 임시저장글 개수
    val tempSaveCount: StateFlow<Int> get() = _tempSaveCount

    private val _basicOptions = MutableStateFlow(true)
    val basicOptions: StateFlow<Boolean> = _basicOptions

    private val _thumbNailUri = MutableStateFlow<Uri?>(null)
    val thumbNailUri: StateFlow<Uri?> = _thumbNailUri

    private val _textOptionsTitle = MutableStateFlow(false)
    val textOptionsTitle: StateFlow<Boolean> get() = _textOptionsTitle

    private val _textOptionsSubtitle = MutableStateFlow(false)
    val textOptionsSubtitle: StateFlow<Boolean> get() = _textOptionsSubtitle

    private val _textOptionsMainText = MutableStateFlow(false)
    val textOptionsMainText: StateFlow<Boolean> get() = _textOptionsMainText

    private val _textOptionsBold = MutableStateFlow(false)
    val textOptionsBold: StateFlow<Boolean> get() = _textOptionsBold

    private val _imageList = MutableStateFlow<MutableList<String?>>(mutableListOf())
    val imageList: StateFlow<List<String?>> get() = _imageList

    private val _uiStatus: MutableStateFlow<UiStatus> = MutableStateFlow(UiStatus.IDLE)
    val uiStatus: StateFlow<UiStatus> get() = _uiStatus

    // 여행소식 등록 조건
    // 표지 이미지 등록, 표지 제목 등록, 최소 500자 이상, 이미지 5장 이상
    val allConditionSatisfied: StateFlow<Boolean> = combine(
        _thumbNailUri, _titleLength, _contentLength, _imageList
    ) { titleImage, titleLength, contentLength, imageList ->
        titleImage != null && titleLength != 0 && contentLength >= 50 && imageList.size >= 1
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    // 임시저장 리스트 관련
    private val _tempSaveListIndex = MutableStateFlow(-1)
    val tempSaveListIndex: StateFlow<Int> get() = _tempSaveListIndex

    private val _tempSaveList = MutableStateFlow<List<Location>>(listOf()) //  Location -> TempSave
    val tempSaveList: StateFlow<List<Location>> get() = _tempSaveList

    fun deleteTempSave(pos: Int) {
        // 해당 위치의 아이템 삭제
    }

    // 위치 검색 리스트 관련
    private val _locationListIndex = MutableStateFlow(-1)
    val locationListIndex: StateFlow<Int> get() = _locationListIndex

    private val _tagList = MutableStateFlow<MutableList<String>>(mutableListOf())
    val tagList: StateFlow<List<String>> get() = _tagList

    private val _locationAdd = MutableStateFlow("")
    val locationAdd: StateFlow<String> = _locationAdd

    private val _locationList = MutableStateFlow<List<Location>>(listOf())
    val locationList: StateFlow<List<Location>> get() = _locationList

    fun addLocationTag(location: String) {
        _tagList.value.add(location)
    }

    fun addImage(uri: String) {
        _imageList.value.add(uri)
    }

    fun searchLocation(location: String) {
        viewModelScope.launch {
            locationUseCase(location).collect {
                if (it.isNullOrEmpty().not())
                    _locationList.value = it as List<Location>
            }
        }
    }

    fun setUiStatus(status: UiStatus) {
        _uiStatus.value = status
    }

    fun setLocation(location: String) {
        _locationAdd.value = location
    }

    fun setLocationListIndex(index: Int) {
        _locationListIndex.value = index
    }

    fun initLocationList() {
        _locationList.value = listOf()
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

    fun setTextLength(len: Int) {
        _contentLength.value = len
    }

    fun setTitleLength(len: Int) {
        _titleLength.value = len
    }

    fun setTitleImageUri(uri: Uri?) {
        uri?.let {
            _thumbNailUri.value = it
        }
    }

    fun saveTripNews(title: String, content: String, context: Context): Flow<Boolean> {
        val fileList: MutableList<File> = mutableListOf()

        imageList.value.map { item ->
            item?.let { uri ->
                val path: String? = context.getImagePathFromURI(Uri.parse(uri))
                path?.let { File(path) }
            }?.let { file -> fileList.add(file) }
        }

        val thumbNailFile =
            thumbNailUri.value?.let { context.getImagePathFromURI(it) }?.let { File(it) }

        return saveTripNewsUseCase(
            context,
            title,
            content,
            thumbNailFile!!,
            fileList,
            tagList.value
        )
    }


    inner class JavaInterface {
        @JavascriptInterface
        fun removeImageItem(uri: String) {
            _imageList.value.remove(uri)
        }

        @JavascriptInterface
        fun removeTagItem(tag: String) {
            _tagList.value.remove(tag)
        }
    }

    enum class UiStatus {
        IDLE,
        TITLE_GALLERY,
        CONTENT_GALLERY,
        LOCATION,
        TEMP_SAVE,
        TEMP_SAVE_SUCCESS,
        HIDE_KEYBOARD,
        TITLE,
        SUBTITLE,
        MAIN_TEXT,
        BOLD,
        NEWS_ADD
    }

}
