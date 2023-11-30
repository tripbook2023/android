package com.tripbook.tripbook.views.trip.detail

import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.viewmodel.TripDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripDetailFragment : BaseFragment<FragmentTripDetailBinding, TripDetailViewModel>(R.layout.fragment_trip_detail) {

    @Inject
    lateinit var detailFactory: TripDetailViewModel.DetailAssistedFactory

    private val args by navArgs<TripDetailFragmentArgs>()

    override val viewModel: TripDetailViewModel by viewModels {
        TripDetailViewModel.provideFactory(detailFactory, args.articleId)
    }


    override fun init() {
        with(binding) {
            Log.d("TRIPBOOK", "DETAIL SCREEN : ${args.articleId}")
            viewModel = this@TripDetailFragment.viewModel
            val items = ArrayList<String>()

            //서버에서 가져온 아티클 데이터 기반으로 수정하기

            for (i in 1..4) {
                items.add("$i")
            }

            val recyclerView: RecyclerView = rv
            val recyclerViewAdapter = TripDetailRecyclerViewAdapter(items)

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerViewAdapter
            }

            val fadeInAnim = AnimationUtils.loadAnimation(
                requireContext(),
                com.tripbook.tripbook.core.design.R.anim.trip_detail_fade_in
            )
            val fadeOutAnim = AnimationUtils.loadAnimation(
                requireContext(),
                com.tripbook.tripbook.core.design.R.anim.trip_detail_fade_out
            )

            var isLiked = false

            //좋아요
            like.setOnClickListener {
                //아티클id 전달

                //색상 변경 (색 x -> 주황색, 색 o -> 다시 색 없애기)
                //좋아요 취소도 다 서버로?

                if(isLiked) { //이미 좋아요 O
                    //viewModel.likeArticle(ArticleId) //아티클id 보내주면서 라이크 api 호출
                    like.clearColorFilter()
                    isLiked = false
                } else {
                    //viewModel.likeArticle(ArticleId) //아티클id 보내주면서 라이크 api 호출

                    val likeColor = ContextCompat.getColor(requireContext(), R.color.tripBook_main)

                    like.setColorFilter(likeColor)
                    isLiked = true

                    //서버에서 가져온 상세보기 중 좋아요 수 넣어주기

                }
            }


            val appBar = appBarLayout
            val bottomBar = tripDetailBottom
            val sideBar = sideProfile
            var toolBar = toolbar
            val icnBefore = icnBefore
            val icnDefault = icnMainDefault

            var isViewsVisible = false

            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

                val totalScroll =
                    Math.abs(verticalOffset)
                        .toFloat() / appBarLayout.totalScrollRange.toFloat() // 백분율로 계산하기

                val navScroll = 0.2f
                val toolBarHide = 0.5f
                val toolBarScroll = 0.95f

                if (totalScroll >= navScroll) {
                    if (!isViewsVisible) {
                        // 본문 내용 보일 때
                        isViewsVisible = true
                        bottomBar.startAnimation(fadeInAnim)
                        sideBar.startAnimation(fadeInAnim)
                        bottomBar.visibility = View.VISIBLE
                        sideBar.visibility = View.VISIBLE
                    }
                } else {
                    if (isViewsVisible) {
                        isViewsVisible = false
                        bottomBar.startAnimation(fadeOutAnim)
                        sideBar.startAnimation(fadeOutAnim)
                        bottomBar.visibility = View.GONE
                        sideBar.visibility = View.GONE
                    }
                }

                if (totalScroll in toolBarHide..toolBarScroll) {
                    toolBar.visibility = View.GONE
                } else {
                    toolBar.visibility = View.VISIBLE
                }

                if (totalScroll >= toolBarScroll) {
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
                    icnBefore.colorFilter = null
                    icnDefault.colorFilter = null
                }
            }
        }
    }
}
