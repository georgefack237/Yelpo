package com.health13.yelpo.presentation.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.health13.yelpo.R
import com.health13.yelpo.RestaurantsAdapter
import com.health13.yelpo.databinding.ActivityMainBinding
import com.health13.yelpo.presentation.viewmodels.HomeViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var homeViewModel: HomeViewModel
    lateinit var restaurantsAdapter: RestaurantsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        restaurantsAdapter = RestaurantsAdapter(this)



        binding.svSearch.setOnQueryTextFocusChangeListener { thisView, hasFocus ->
            if (hasFocus) {
                thisView.clearFocus() // onResume the focus will be cleared
                openSearchActivity()
            }
        }

      homeViewModel.categories.observe(this) { categories ->
            for (category in categories) {
                val chip =
                    layoutInflater.inflate(R.layout.item_genre_home,  binding.cgGenreList, false) as Chip
                chip.text = category.title
                binding.cgGenreList.addView(chip as View)
            }
        }

        // set chip group checked change listener
        binding.cgGenreList.setOnCheckedChangeListener { group, checkedId ->
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
        binding.businessList.layoutManager = LinearLayoutManager(this)


        populateCategories()
        handleProgressBar()
        populate()


    }

    private fun populate(){
       homeViewModel.yelpRestaurants.observe(this){
        restaurantsAdapter.restaurants = it
           restaurantsAdapter.notifyDataSetChanged()
       }

    }


    private fun populateByCategory(category: String){

        homeViewModel.getBusinessByCategory(category)
        homeViewModel.categoryBusinesses.observe(this){
            restaurantsAdapter.restaurants  = it
            restaurantsAdapter.notifyDataSetChanged()
        }



    }

    private fun populateCategories(){
        homeViewModel.categories.observe(this){

            for (item in it){
                Log.e(ContentValues.TAG, "::::Category ==== ${item.title}")
            }

        }
    }


    private fun openSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
    private fun handleProgressBar() {
        homeViewModel.progressBar.observe(this) { isLoading ->

            if(!isLoading){
                binding.shimmerPlaceHolder.stopShimmer()
                binding.shimmerPlaceHolder.visibility = View.GONE
            }

        }
    }


}