package com.health13.yelpo.presentation.activities
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.health13.yelpo.RestaurantsAdapter
import com.health13.yelpo.databinding.ActivitySearchBinding
import com.health13.yelpo.presentation.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel
    private lateinit var restaurantsAdapter: RestaurantsAdapter
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        restaurantsAdapter = RestaurantsAdapter(this)


        binding.svSearchQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
           androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val textSubmitted: String = binding.svSearchQuery.query.toString()
                binding.svSearchQuery.clearFocus() // make keyboard disappear
                searchViewModel.searchBusiness(textSubmitted)
                return true // the query has been handled by the listener
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false // default search widget action
            }
        })



        binding.rvMovieList.adapter = restaurantsAdapter
        binding.rvMovieList.layoutManager = LinearLayoutManager(this)

        populateCategories()
        handleSearchNotFound()
        handleProgressBar()
        populate()

    }



    private fun populateCategories(){
        searchViewModel.categories.observe(this){

            for (item in it){
                Log.e(ContentValues.TAG, "::::Category ==== ${item.title}")
            }

        }
    }

    private fun handleSearchNotFound() {
        searchViewModel.isSearchResultsEmpty.observe(this) { emptyResult ->
            binding.grpSearchNotFound.isVisible = emptyResult
        }
    }

    private fun handleProgressBar() {
        searchViewModel.progressBar.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }


    private fun populate(){
       searchViewModel.businesses.observe(this){
            restaurantsAdapter.restaurants = it
            restaurantsAdapter.notifyDataSetChanged()
        }

    }


}