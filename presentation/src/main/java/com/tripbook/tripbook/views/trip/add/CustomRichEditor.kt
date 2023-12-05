package com.tripbook.tripbook.views.trip.add

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import jp.wasabeef.richeditor.RichEditor

class CustomRichEditor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RichEditor(context, attrs) {

    var locationIdx = 0

    init {
        setEditorFontSize(13)
    }

    private lateinit var parentScrollView: NestedScrollView
    private var scrollSize = 0

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onChangeListener()
    }

    private fun onChangeListener() {
        this.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
            scrollSize = oldScrollY
            // editor의 스크롤이 바뀐만큼 parent 스크롤뷰에서 nestedScroll을 수동으로 조정
            parentScrollView.onNestedScroll(parentScrollView, 0, 0, 0, 38)
        }
    }

    fun setParentScrollView(parentScroll: NestedScrollView) {
        parentScrollView = parentScroll
    }

    fun insertImageC(uri: String, alt: String, width: Int, imageIdx: Int){
        exec("javascript:RE.prepareInsert();")
        exec("javascript:RE.insertImageC('$uri', '$alt','$width', '$imageIdx');")
    }

    fun insertLocation(location: String){
        exec("javascript:RE.prepareInsert();")
        exec("javascript:RE.insertLocation('$location', '$locationIdx');")
        locationIdx += 1
    }
}