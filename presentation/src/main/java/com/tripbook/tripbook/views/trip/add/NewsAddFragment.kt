package com.tripbook.tripbook.views.trip.add

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentNewsAddBinding
import com.tripbook.tripbook.utils.convertPxToDp
import com.tripbook.tripbook.utils.getImagePathFromURI
import com.tripbook.tripbook.viewmodel.NewsAddViewModel
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.Timer
import java.util.TimerTask


class NewsAddFragment :
    BaseFragment<FragmentNewsAddBinding, NewsAddViewModel>(R.layout.fragment_news_add) {

    override val viewModel: NewsAddViewModel by activityViewModels()
    private val regex = Regex("<[^>]*>?")
    private val timer = Timer()

    private fun periodicSave() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                viewLifecycleOwner.lifecycleScope.launch {
                    if (binding.title.text.toString() != "" && binding.mainEditor.html != null) {
                        viewModel.saveTempArticle(
                            binding.title.text.toString(),
                            binding.mainEditor.html
                        ).collect {
                            it?.let {
                                viewModel.setTempId(it)
                                viewModel.getTempList()
                            }
                        }
                    }
                }
            }
        }, 60000, 60000)
    }

    private fun createFile(uri: Uri): File? { // uri -> 이미지 경로 -> 파일 반환
        val path = requireContext().getImagePathFromURI(uri)
        return path?.let { File(it) }
    }

    private val thumbnailGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                createFile(it)?.let { file ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.uploadImage(file).collect { image ->
                            image?.let { img ->
                                viewModel.setThumbnail(img.url)
                                viewModel.setThumbnailId(img.id.toInt())
                            }
                        }
                    }
                }
            }
        }
    
    private val contentsGalleryLauncher =
        // 최대 30장, 최소 1장
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(30)) { uris ->
            if (uris.isNotEmpty()) {
                for (uri in uris) {
                    val display = requireContext().resources.displayMetrics
                    val deviceWidth = display.widthPixels
                    createFile(uri)?.let { file ->
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.uploadImage(file).collect { image ->
                                image?.let {
                                    binding.mainEditor.insertImageC(
                                        it.url,
                                        "",
                                        (requireContext().convertPxToDp(deviceWidth) * 0.9).toInt(),
                                        it.id.toInt()
                                    )
                                    viewModel.addImage(image.id.toInt())
                                }
                            }
                        }
                    }
                }
            }
        }

    override fun init() {
        binding.viewModel = viewModel
        binding.mainEditor.setParentScrollView(binding.scrollView)

        viewModel.getTempList()
        collectData()
        setTextLength()
        initListener()
        periodicSave()

        binding.mainEditor.apply {
            setPlaceholder(resources.getString(R.string.news_add_editText_hint))
            setPadding(20, 20, 20, 0)
            addJavascriptInterface(viewModel.JavaInterface(), "android")
            setOnDecorationChangeListener { _, types ->
                if (types.contains(RichEditor.Type.BOLD)) {
                    viewModel.setTextOptions(binding.textOptionsBold, true)
                } else {
                    viewModel.setTextOptions(binding.textOptionsBold, false)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.locationAdd.collect { location ->
                if (location != null) {
                    binding.mainEditor.insertLocation(location.place_name)
                    viewModel.addLocationTag(location)
                    viewModel.setLocationListIndex(-1)
                    viewModel.setLocation(null)
                }
            }
        }
    }

    private fun initListener() = binding.run {
        upButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun collectData() {
        // 클릭 리스너 연결을 한 번에 묶음
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStatus.collect { status ->
                    when (status) {
                        NewsAddViewModel.UiStatus.NEWS_ADD -> {
                            viewLifecycleOwner.lifecycleScope.launch {
                                binding.mainEditor.html?.let {
                                    val replace =
                                        binding.mainEditor.html.replace("visible", "hidden")
                                            .replace("imgBorderOn", "imgBorderOff").replace(
                                            "2px solid rgb(255, 78, 22)",
                                            "0px solid transparent"
                                        )

                                    viewModel.saveTripNews(
                                        binding.title.text.toString(),
                                        replace
                                    ).collect {
                                        it?.let {
                                            findNavController().navigate(
                                                NewsAddFragmentDirections.actionNewsAddFragmentToTripDetailFragment(
                                                    it
                                                )
                                            )
                                        } ?: run {
                                            // 여행소식 등록 실패
                                            Toast.makeText(
                                                requireContext(),
                                                "여행소식 등록에 실패하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }

                                viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)

                            }
                        }

                        NewsAddViewModel.UiStatus.TEMP_SAVE_SUCCESS -> {
                            if (binding.title.length() > 0 && !binding.mainEditor.html.isNullOrEmpty()) {
                                viewLifecycleOwner.lifecycleScope.launch {
                                    val replace =
                                        binding.mainEditor.html.replace("imgBorderOn", "imgBorderOff").replace(
                                                "2px solid rgb(255, 78, 22)",
                                                "0px solid transparent"
                                            )

                                    viewModel.saveTempArticle(
                                        binding.title.text.toString(),
                                        replace
                                    ).collect {
                                        it?.let {
                                            TempSaveAlertDialogFragment().show(
                                                requireActivity().supportFragmentManager,
                                                "TempSaveAlertDialogFragment"
                                            )
                                            viewModel.setTempId(it)
                                            viewModel.getTempList()
                                        } ?: run {
                                            Toast.makeText(
                                                requireContext(),
                                                "여행소식 임시저장에 실패하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "제목과 본문을 한 글자 이상 작성해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.SELECT_TEMP -> {
                            setTempArticle()
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.TITLE_GALLERY -> {
                            thumbnailGalleryLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.CONTENT_GALLERY -> {
                            contentsGalleryLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.LOCATION -> {
                            NewsAddLocationBottomFragment().show(parentFragmentManager, "location")
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.TEMP_SAVE -> {
                            TempSaveDialogFragment().show(
                                requireActivity().supportFragmentManager, "TempSaveFragment"
                            )
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.HIDE_KEYBOARD -> {
                            hideKeyboard()
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.TITLE -> {
                            viewModel.setTextOptions(binding.textOptionsTitle, true)
                            binding.mainEditor.setFontSize(4)
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.SUBTITLE -> {
                            viewModel.setTextOptions(binding.textOptionsSubtitle, true)
                            binding.mainEditor.setFontSize(3)
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.MAIN_TEXT -> {
                            viewModel.setTextOptions(binding.textOptionsMainText, true)
                            binding.mainEditor.setFontSize(2)
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.BOLD -> {
                            viewModel.setTextOptions(binding.textOptionsBold, null)
                            binding.mainEditor.setBold()
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        else -> {} // Idle
                    }
                }
            }
        }
    }

    private fun setTempArticle() {
        val tempArticle =
            viewModel.tempSaveList.value[viewModel.tempSaveListIndex.value]
        binding.title.setText(tempArticle.title)
        binding.mainEditor.html = tempArticle.content
        viewModel.setThumbnail(tempArticle.thumbnailUrl)
        viewModel.setTempId(tempArticle.id)
        tempArticle.location?.let { viewModel.setLocationList(it.toMutableList()) }
        viewModel.setContentLength(
            regex.replace(binding.mainEditor.html, "")
                .replace("&nbsp;", " ").length
        )
    }

    private fun setTextLength() {
        binding.title.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitleLength(text!!.length)
        }
        binding.mainEditor.setOnTextChangeListener {
            Timber.tag("텍스트 리스너").d("${it.length} : $it")
            val contents = regex.replace(it, "").replace("&nbsp;", " ")
            viewModel.setContentLength(contents.length)
            setTextOption()
        }
    }

    private fun setTextOption() {
        binding.mainEditor.evaluateJavascript("javascript:RE.checkSelection()") { size ->
            if (size != "null") {
                when (size) {
                    "\"4\"" -> viewModel.setTextOptions(binding.textOptionsTitle, true)
                    "\"3\"" -> viewModel.setTextOptions(binding.textOptionsSubtitle, true)
                    "\"2\"" -> viewModel.setTextOptions(binding.textOptionsMainText, true)
                }
            } else {
                viewModel.setTextOptions(binding.textOptionsMainText, false)
                viewModel.setTextOptions(binding.textOptionsSubtitle, false)
                viewModel.setTextOptions(binding.textOptionsTitle, false)
            }
        }
    }

    override fun onStop() {
        activity?.viewModelStore?.clear()
        timer.cancel()
        super.onStop()
    }
}
