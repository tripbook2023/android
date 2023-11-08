package com.tripbook.tripbook.views.tripAdd

import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.utils.convertPxToDp
import com.tripbook.tripbook.databinding.FragmentNewsAddBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.launch
import timber.log.Timber


class NewsAddFragment :
    BaseFragment<FragmentNewsAddBinding, NewsAddViewModel>(R.layout.fragment_news_add) {

    override val viewModel: NewsAddViewModel by activityViewModels()

    private val titleGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.setTitleImageUri(uri)
            }
        }
    private val imagesGalleryLauncher =
        // 최대 30장, 최소 1장
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(30)) { uris ->
            if (uris.isNotEmpty()) {
                for (uri in uris) {
                    val display = requireContext().resources.displayMetrics
                    val deviceWidth = display.widthPixels
                    binding.mainEditor.insertImageC(
                        uri.toString(),
                        "",
                        (requireContext().convertPxToDp(deviceWidth) * 0.9).toInt()
                    )
                    viewModel.addImage(uri.toString())
                }
            }
        }

    override fun init() {
        binding.viewModel = viewModel
        binding.mainEditor.setParentScrollView(binding.scrollView)

        collectData()
        setTextLength()

        binding.mainEditor.apply {
            setPlaceholder(resources.getString(R.string.news_add_editText_hint))
            setPadding(20, 20, 20, 0)
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
                if (location != "") {
                    binding.mainEditor.insertLocation(location)
                    viewModel.addLocationTag(location)
                    viewModel.setLocationListIndex(-1)
                    viewModel.setLocation("")
                }
            }
        }
    }

    private fun collectData() {
        // 클릭 리스너 연결을 한 번에 묶음
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStatus.collect { status ->
                    when (status) {
                        NewsAddViewModel.UiStatus.NEWS_ADD -> {
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.saveTripNews(
                                    binding.title.text.toString(),
                                    binding.mainEditor.html,
                                    requireContext()
                                ).collect{
                                    if(it){
                                        // 여행소식 등록 성공
                                        Toast.makeText(requireContext(), "여행소식 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                                    }else{
                                        // 여행소식 등록 실패
                                        Toast.makeText(requireContext(), "여행소식 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        NewsAddViewModel.UiStatus.TITLE_GALLERY -> {
                            titleGalleryLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                            viewModel.setUiStatus(NewsAddViewModel.UiStatus.IDLE)
                        }

                        NewsAddViewModel.UiStatus.CONTENT_GALLERY -> {
                            imagesGalleryLauncher.launch(
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

                        NewsAddViewModel.UiStatus.TEMP_SAVE_SUCCESS -> {
                            TempSaveAlertDialogFragment().show(
                                requireActivity().supportFragmentManager,
                                "TempSaveAlertDialogFragment"
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

    private fun setTextLength() {
        binding.title.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitleLength(text!!.length)
        }
        binding.mainEditor.setOnTextChangeListener {
            Timber.tag("텍스트 리스너").d("${it.length} : $it")
            val regex = Regex("<[^>]*>?")
            val contents = regex.replace(it, "").replace("&nbsp;", " ")
            viewModel.setTextLength(contents.length)
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
}



