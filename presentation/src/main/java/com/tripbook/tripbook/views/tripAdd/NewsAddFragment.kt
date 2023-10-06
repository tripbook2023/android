package com.tripbook.tripbook.views.tripAdd

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.Utils.convertPxToDp
import com.tripbook.tripbook.Utils.getImagePathFromURI
import com.tripbook.tripbook.databinding.FragmentNewsAddBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.launch


class NewsAddFragment :
    BaseFragment<FragmentNewsAddBinding, NewsAddViewModel>(R.layout.fragment_news_add) {

    override val viewModel: NewsAddViewModel by activityViewModels()

    private val titleGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val fullPath = getImagePathFromURI(uri, requireContext())
                viewModel.setTitleImageUri(uri, fullPath)
            }
        }
    private val imagesGalleryLauncher =
        // 최대 30장, 최소 5장
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(30)) { uris ->
            if (uris.isNotEmpty()) {
                for (uri in uris) {
                    val display = requireContext().resources.displayMetrics
                    val deviceWidth = display.widthPixels
                    binding.mainEditor.insertImageC(
                        uri.toString(),
                        "",
                        (convertPxToDp(deviceWidth, requireContext()) * 0.8).toInt()
                    )
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
                    viewModel.setListIndex(-1)
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
            Log.d("텍스트 리스너", it.length.toString() + ": " + it)
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



