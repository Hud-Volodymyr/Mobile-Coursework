package ua.kpi.comsys.ip8405.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.kpi.comsys.ip8405.MainFragment
import ua.kpi.comsys.ip8405.R
import ua.kpi.comsys.ip8405.ui.bookList.BookListFragment
import ua.kpi.comsys.ip8405.ui.chart.ChartFragment
import ua.kpi.comsys.ip8405.ui.gallery.GalleryFragment
import ua.kpi.comsys.ip8405.ui.home.HomeFragment

class NavFragment : MainFragment() {
    private lateinit var viewModel: NavViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(NavViewModel::class.java)
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val navigationView = view?.findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView?.selectedItemId = when (viewModel.state.value) {
            NavViewModel.State.Home -> R.id.navigation_home
            NavViewModel.State.Charts -> R.id.navigation_charts
            NavViewModel.State.Books -> R.id.navigation_books
            NavViewModel.State.Gallery -> R.id.navigation_gallery
            else -> error("Navigating to inexistant fragment")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navPager = view.findViewById<ViewPager2>(R.id.nav_pager)

        navPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 4

            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> HomeFragment()
                1 -> ChartFragment()
                2 -> BookListFragment()
                3 -> GalleryFragment()
                else -> error("Navigating to inexistant fragment")
            }
        }
        val navView = view.findViewById<BottomNavigationView>(R.id.navigation_view)

        navPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.selectedItemId = when (position) {
                    0 -> R.id.navigation_home
                    1 -> R.id.navigation_charts
                    2 -> R.id.navigation_books
                    3 -> R.id.navigation_gallery
                    else -> error("Navigating to inexistant fragment")
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                NavViewModel.State.Home -> navPager.currentItem = 0
                NavViewModel.State.Charts -> navPager.currentItem = 1
                NavViewModel.State.Books -> navPager.currentItem = 2
                NavViewModel.State.Gallery -> navPager.currentItem = 3
                else -> error("Navigating to inexistant fragment: $it")
            }
        }

        navView.setOnNavigationItemSelectedListener {
            viewModel.state.value = when (it.itemId) {
                R.id.navigation_home -> NavViewModel.State.Home
                R.id.navigation_charts -> NavViewModel.State.Charts
                R.id.navigation_books -> NavViewModel.State.Books
                R.id.navigation_gallery -> NavViewModel.State.Gallery
                else -> error("Navigating to inexistant fragment: ${it.itemId}")
            }
            true
        }
    }
}