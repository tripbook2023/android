package com.tripbook.tripbook.views.trip.detail

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.viewmodel.ArticleViewModel
import com.tripbook.tripbook.viewmodel.TripDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
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

        Log.d("아이디", "DETAIL SCREEN : ${args.articleId}")

        val binding = binding

        val webView = binding.mainEditor

        viewModel.loadArticleDetailInfo(webView)

        var isLiked = false

        //좋아요
        binding.like.setOnClickListener {

            if(isLiked) {

                articleViewModel.likeArticle(args.articleId)
                val like = ContextCompat.getDrawable(requireContext(), com.tripbook.tripbook.core.design.R.drawable.icn_like_24)

                val currentHeartNum = articleViewModel.heartNum.value ?: 0
                articleViewModel.setHeartNum(currentHeartNum - 1)

                binding.like.setImageDrawable(like)
                isLiked = false
            } else {
                articleViewModel.likeArticle(args.articleId)

                val like = ContextCompat.getDrawable(requireContext(), com.tripbook.tripbook.core.design.R.drawable.icn_like_active_24)

                val currentHeartNum = articleViewModel.heartNum.value ?: 0
                articleViewModel.setHeartNum(currentHeartNum + 1)

                binding.like.setImageDrawable(like)
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
            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

                val totalScroll =
                    Math.abs(verticalOffset)
                        .toFloat() / appBarLayout.totalScrollRange.toFloat() // 백분율로 계산하기

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
                } else {
                    toolBar.visibility = View.GONE
                }

            }
        } //binding
    }

}
