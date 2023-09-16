import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentTripDetailBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel
import com.tripbook.tripbook.views.trip.detail.TripDetailRecyclerViewAdapter

class TripDetailFragment :
    BaseFragment<FragmentTripDetailBinding, LoginViewModel>(R.layout.fragment_trip_detail) {
    override val viewModel: LoginViewModel by activityViewModels()

    override fun init() {
        val binding = binding

        with(binding) {
            viewModel = this@TripDetailFragment.viewModel
            val items = ArrayList<String>()

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

            appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->

                val totalScroll =
                    Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat() // 백분율로 계산하기

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
            })
        }
    }
}
