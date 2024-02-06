package com.tripbook.tripbook.views.trip.detail

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.viewmodel.ArticleViewModel
import com.tripbook.tripbook.viewmodel.TripDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TripDetailFragment : BaseFragment<FragmentTripDetailBinding, TripDetailViewModel>(R.layout.fragment_trip_detail) {

    @Inject
    lateinit var detailFactory: TripDetailViewModel.DetailAssistedFactory

    private val articleViewModel: ArticleViewModel by activityViewModels()

    private val args by navArgs<TripDetailFragmentArgs>()

    override val viewModel: TripDetailViewModel by viewModels {
        TripDetailViewModel.provideFactory(detailFactory, args.articleId)
    }

    override fun init() {
        binding.viewModel = viewModel
        binding.articleViewModel = articleViewModel

        val binding = binding

        val webView = binding.mainEditor

        viewModel.loadArticleDetailInfo(webView)
        articleViewModel.getUserList()

        viewModel.viewModelScope.launch {
            viewModel.articleDetail.collect { detail ->

                val likeImageResource = if (detail?.heart == true) {
                    com.tripbook.tripbook.core.design.R.drawable.icn_like_active_24
                } else {
                    com.tripbook.tripbook.core.design.R.drawable.icn_like_24
                }

                var author = detail?.author?.name
                if (author != null) {
                    articleViewModel.authorChk(author)
                }
                var authorChk = articleViewModel.author.value

                updateUI(detail, authorChk, likeImageResource)

            }
        }

        //좋아요
        binding.like.setOnClickListener {

            articleViewModel.likeArticle(args.articleId)

            var isLiked = articleViewModel.heart.value

            isLiked = !isLiked

            val likeImageResource = if (isLiked) {
                com.tripbook.tripbook.core.design.R.drawable.icn_like_active_24
            } else {
                com.tripbook.tripbook.core.design.R.drawable.icn_like_24
            }
            binding.like.setImageResource(likeImageResource)

        }

        //뒤로가기
        binding.icnBefore.setOnClickListener {
            findNavController().popBackStack()
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
            val icnMainReport = icnMainReport

            var isViewsVisible = false

            //appbar 관련 이벤트
            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

                val totalScroll =
                    Math.abs(verticalOffset)
                        .toFloat() / appBarLayout.totalScrollRange.toFloat()

                //이벤트 추후 QA 하면서 추가
                if (totalScroll > 0.5) {
                    //sideBar.visibility = View.GONE
                    //sideBar.startAnimation(fadeOutAnim)
                } else {
                    //sideBar.visibility = View.VISIBLE
                    //sideBar.startAnimation(fadeInAnim)
                }

                if (verticalOffset == 0) {
                    toolBar.visibility = View.VISIBLE
                    icnBefore.colorFilter = null
                    icnDefault.colorFilter = null
                    icnMainReport.colorFilter = null
                } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    toolBar.visibility = View.VISIBLE
                    icnBefore.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    icnDefault.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    icnMainReport.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                } else {
                    toolBar.visibility = View.GONE
                }

            }
        } //binding
    }

    private fun updateUI(detail: ArticleDetail?, authorChk: Boolean, likeImageResource: Int) {
        with(binding) {
            detail?.let {
                if (authorChk) {
                    icnMainReport.visibility = View.GONE
                    icnMainDefault.setImageResource(com.tripbook.tripbook.core.design.R.drawable.icn_more_default_24)
                } else {
                    icnMainReport.visibility = View.VISIBLE
                    icnMainDefault.setImageResource(com.tripbook.tripbook.core.design.R.drawable.icn_more_active_24)
                }

                it.heartNum.let { articleViewModel?.setHeartNum(it) }
                it.heart.let { articleViewModel?.setHeart(it) }

                like.setImageResource(likeImageResource)
            }
        }
    }

}


