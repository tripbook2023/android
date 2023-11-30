package com.tripbook.tripbook.views.trip.detail

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.domain.model.Image
import com.tripbook.tripbook.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TripDetailFragment :
    BaseFragment<FragmentTripDetailBinding, ArticleViewModel>(R.layout.fragment_trip_detail) {
    override val viewModel: ArticleViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel

        val binding = binding

        //여행소식 상세보기 api
        viewLifecycleOwner.lifecycleScope.launch {
            val articleId: Long = 42 //test용

            viewModel.getArticleDetail(articleId)

            viewModel.articleDetailInfo.collect {

                viewModel.content.onEach { content ->
                    val webView = binding.webView

                    if (!content.isNullOrBlank()) {
                        val modifiedContent =  replaceImagePlaceholders(content, it?.imageList.orEmpty())

                        webView.visibility = View.VISIBLE
                        webView.loadDataWithBaseURL(null, modifiedContent, "text/html", "UTF-8", null)

                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

        }

        var isLiked = false

        //좋아요
        binding.like.setOnClickListener {

            val articleId: Long = 19 //test용

            if(isLiked) {
                viewModel.likeArticle(articleId)

                val currentHeartNum = viewModel.heartNum.value
                if (currentHeartNum != null) {
                    viewModel.setHeartNum(currentHeartNum - 1)
                }

                binding.like.clearColorFilter()
                isLiked = false
            } else {
                viewModel.likeArticle(articleId)

                var likeColor = ContextCompat.getColor(requireContext(), R.color.tripBook_main)

                val currentHeartNum = viewModel.heartNum.value
                if (currentHeartNum != null) {
                    viewModel.setHeartNum(currentHeartNum + 1)
                }
                binding.like.setColorFilter(likeColor)
                isLiked = true

            }
        }

        with(binding) {
            viewModel = this@TripDetailFragment.viewModel

            val fadeInAnim = AnimationUtils.loadAnimation(
                requireContext(),
                com.tripbook.tripbook.core.design.R.anim.trip_detail_fade_in
            )
            val fadeOutAnim = AnimationUtils.loadAnimation(
                requireContext(),
                com.tripbook.tripbook.core.design.R.anim.trip_detail_fade_out
            )

            val appBar = appBarLayout
            val bottomBar = tripDetailBottom
            val sideBar = sideProfile
            var toolBar = toolbar
            val icnBefore = icnBefore
            val icnDefault = icnMainDefault

            var isViewsVisible = false

            //appbar 관련 이벤트
            appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val totalScroll =
                    Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()

                if(totalScroll > 0.5) {
                    sideBar.visibility = View.GONE
                    //sideBar.startAnimation(fadeOutAnim)
                } else {
                    sideBar.visibility = View.VISIBLE
                    //sideBar.startAnimation(fadeInAnim)
                }

                if (verticalOffset == 0) {
                    toolBar.visibility = View.VISIBLE
                    icnBefore.colorFilter = null
                    icnDefault.colorFilter = null
                } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    toolBar.visibility = View.VISIBLE
                    icnBefore.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                    icnDefault.setColorFilter(ContextCompat.getColor(requireContext(),R.color.black))
                } else {
                    toolBar.visibility = View.GONE
                }


            })
        } //binding
    }

    private fun replaceImagePlaceholders(content: String, imageList: List<Image>): String {

        var modifiedContent = content

        imageList.forEachIndexed { index, imageItem ->
            val imageUrl = imageItem.url
            val placeholder = "id=\"image$index\""
            modifiedContent = modifiedContent.replace(placeholder, "src=\"$imageUrl\"")
        }

        return modifiedContent
    }


}
