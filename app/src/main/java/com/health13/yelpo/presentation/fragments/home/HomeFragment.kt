package com.health13.yelpo.presentation.fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.health13.yelpo.presentation.adapters.RestaurantsAdapter
import com.health13.yelpo.databinding.FragmentHomeBinding
import com.health13.yelpo.presentation.activities.SearchActivity
import com.health13.yelpo.presentation.adapters.PagerSectionAdapter
import com.health13.yelpo.presentation.adapters.TopBusinessAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var homeViewModel: HomeViewModel
    lateinit var restaurantsAdapter: RestaurantsAdapter
    lateinit var topBusinessAdapter:TopBusinessAdapter


    private val binding get() = _binding!!

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        restaurantsAdapter = this.context?.let { RestaurantsAdapter(it) }!!

        topBusinessAdapter = this.context?.let { TopBusinessAdapter(it) }!!

        binding.pagerMovieList.adapter = PagerSectionAdapter(this.activity!!.supportFragmentManager, lifecycle)


        _binding!!.svSearch.setOnQueryTextFocusChangeListener { thisView, hasFocus ->
            if (hasFocus) {
                thisView.clearFocus()
                openSearchActivity()
            }
        }




        handleProgressBar()
        configureTabPager(binding.tabPageSection,binding.pagerMovieList)
        return root
    }






    private fun configureTabPager(tabPagerSection: TabLayout, pagerMovieList: ViewPager2) {
        pagerMovieList.isUserInputEnabled = false
        TabLayoutMediator(tabPagerSection, pagerMovieList){ tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Default View"
                }
                1 -> {
                    tab.text = "Map View"
                }
            }
        }.attach()
    }







    private fun openSearchActivity() {
        val intent = Intent(this.context, SearchActivity::class.java)
        startActivity(intent)
    }


    private fun handleProgressBar() {
        homeViewModel.progressBar.observe(viewLifecycleOwner) { isLoading ->

            if(!isLoading){
                binding.shimmerPlaceHolder.stopShimmer()
                binding.shimmerPlaceHolder.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}