package com.health13.yelpo.presentation.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.health13.yelpo.R
import com.health13.yelpo.databinding.FragmentDefaultViewBinding
import com.health13.yelpo.presentation.adapters.RestaurantsAdapter
import com.health13.yelpo.presentation.activities.SearchActivity
import com.health13.yelpo.presentation.adapters.TopBusinessAdapter
import com.health13.yelpo.presentation.fragments.home.HomeViewModel


class DefaultView : Fragment() {

    private var _binding: FragmentDefaultViewBinding? = null
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

        _binding =  FragmentDefaultViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        restaurantsAdapter = this.context?.let { RestaurantsAdapter(it) }!!

        topBusinessAdapter = this.context?.let { TopBusinessAdapter(it) }!!



        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            for (category in categories) {
                val chip =
                    layoutInflater.inflate(R.layout.item_genre_home,  binding.cgGenreList, false) as Chip
                chip.text = category.title
                _binding!!.cgGenreList.addView(chip as View)
            }
        }

        // set chip group checked change listener
        _binding!!.cgGenreList.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            // Responds to child chip checked
            chip?.let { chipView ->
                if (chipView.isChecked) {
                    populateByCategory(chip.text.toString())
                }
            }
            // All chips are unchecked returns to displaying popular movies
            if (checkedId == -1) {
                populate()
            }
        }

        binding.businessList.adapter = restaurantsAdapter
        binding.businessList.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,true)


        binding.topBusinesses.adapter = topBusinessAdapter
        binding.topBusinesses.layoutManager = LinearLayoutManager(this.context)


        populateCategories()
        populate()
        return root
    }



    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    private fun populate(){
        homeViewModel.yelpRestaurants.observe(viewLifecycleOwner){
            restaurantsAdapter.restaurants = it
            restaurantsAdapter.notifyDataSetChanged()


            topBusinessAdapter.restaurants = it
            topBusinessAdapter.notifyDataSetChanged()
        }

    }







    private fun populateByCategory(category: String){

        homeViewModel.getBusinessByCategory(category)
        homeViewModel.categoryBusinesses.observe(viewLifecycleOwner){
            restaurantsAdapter.restaurants  = it
            restaurantsAdapter.notifyDataSetChanged()

            topBusinessAdapter.restaurants = it
            topBusinessAdapter.notifyDataSetChanged()
        }



    }

    private fun populateCategories(){
        homeViewModel.categories.observe(viewLifecycleOwner){

            for (item in it){
                Log.e(ContentValues.TAG, "::::Category ==== ${item.title}")
            }

        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}