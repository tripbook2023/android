package com.tripbook.tripbook.views.trip.detail

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
        binding.viewModel = viewModel

        val binding = binding

        //여행소식 상세보기 api
        viewLifecycleOwner.lifecycleScope.launch {
            val articleId: Long = 42 //test용

            viewModel.getArticleDetail(articleId)

            viewModel.articleDetailInfo.collect {

                viewModel.content.onEach { content ->
                    val webView = binding.mainEditor

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

            val articleId: Long = 42 //test용

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

            val appBar = appBarLayout
            val bottomBar = tripDetailBottom
            val sideBar = sideProfile
            var toolBar = toolbar
            val icnBefore = icnBefore
            val icnDefault = icnMainDefault

            var isViewsVisible = false

            //appbar 관련 이벤트
            appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            //appbar 관련 이벤트
             appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

                val totalScroll =
                    Math.abs(verticalOffset)
                        .toFloat() / appBarLayout.totalScrollRange.toFloat() // 백분율로 계산하기

                if(totalScroll > 0.5) {
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
