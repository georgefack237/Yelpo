package com.health13.yelpo.presentation.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.health13.yelpo.data.models.Category
import com.health13.yelpo.data.models.CategoryResponse
import com.health13.yelpo.data.models.YelpRestaurant
import com.health13.yelpo.data.models.YelpSearchResult
import com.health13.yelpo.domain.GetBusinessByCategoryUseCase
import com.health13.yelpo.domain.GetCategoriesUseCase
import com.health13.yelpo.domain.SearchBusinessUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private val _businesses = MutableLiveData<List<YelpRestaurant>>()
    private val _isSearchResultsEmpty = MutableLiveData<Boolean>()
    private val _progressBar = MutableLiveData<Boolean>()
    private val _errorGenericLiveData = MutableLiveData<String>()
    private val _categories = MutableLiveData<List<Category>>()
    private val _categoryBusinesses =  MutableLiveData<List<YelpRestaurant>>()

    val businesses: LiveData<List<YelpRestaurant>> = _businesses
    val progressBar :LiveData<Boolean> = _progressBar
    val isSearchResultsEmpty : LiveData<Boolean> = _isSearchResultsEmpty
    val errorGenericLiveData : LiveData<String> = _errorGenericLiveData
    val categories : LiveData<List<Category>> = _categories
    val categoryBusinesses:LiveData<List<YelpRestaurant>> = _categoryBusinesses


    private val searchBusinessUseCase = SearchBusinessUseCase()
    private val getCategoriesUseCase = GetCategoriesUseCase()
    private val getBusinessByCategoryUseCase = GetBusinessByCategoryUseCase()

    init {
        getCategories()
    }




    fun getBusinessByCategory(category: String){
        CoroutineScope(Dispatchers.IO).launch {
            getBusinessByCategoryUseCase.execute(category).enqueue(object :
                Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {

                    Log.i(ContentValues.TAG, "onResponse ${response.body()!!.restaurants}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(ContentValues.TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    }
                    _categoryBusinesses.value = response.body()!!.restaurants
                    _progressBar.postValue(false)
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(ContentValues.TAG, "onFailure $t")

                    _progressBar.postValue(false)
                }
            })
        }
    }

    private fun getCategories(){
        CoroutineScope(Dispatchers.IO).launch {
            getCategoriesUseCase.execute().enqueue(object :
                Callback<CategoryResponse> {
                override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {

                    val body = response.body()
                    if (body == null) {
                        Log.e(ContentValues.TAG, "Did not receive valid response body from Yelp API... exiting $response")
                        return
                    }
                    // Take first ten categories
                    _categories.value = response.body()!!.categories.take(10)
                }
                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure $t")
                }
            })


        }
    }

    fun searchBusiness(query: String){

        CoroutineScope(Dispatchers.IO).launch {
            searchBusinessUseCase.execute(query).enqueue(object :
                Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {

                    Log.i(ContentValues.TAG, "onResponse ${response.body()!!.restaurants}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(ContentValues.TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    }

                   _businesses.value = response.body()!!.restaurants
                    _isSearchResultsEmpty.value = response.body()!!.restaurants.isEmpty()
                    _progressBar.postValue(false)
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(ContentValues.TAG, "onFailure $t")

                    _errorGenericLiveData.postValue("An error occurred: ${t.message}")
                    _progressBar.postValue(false)
                }
            })
        }
    }
}