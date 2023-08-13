package com.tripbook.tripbook.views.trip.detail


import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel


class TripDetailFragment :
    BaseFragment<FragmentTripDetailBinding, LoginViewModel>(R.layout.fragment_trip_detail) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel

        val items = ArrayList<String>()

        for (i in 1..100) {
            items.add("$i")
        }

        val recyclerView: RecyclerView = binding.rv
        val recyclerViewAdapter = RecyclerViewAdapter(items)

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

        val appBar = binding.appBarLayout
        val bottomBar = binding.tripDetailBottom
        val sideBar = binding.sideProfile
        var isViewsVisible = false

        appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            val maxOffset = appBar.totalScrollRange * 1 / 3

            if (Math.abs(verticalOffset) >= maxOffset) {
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
        }
    }

}


