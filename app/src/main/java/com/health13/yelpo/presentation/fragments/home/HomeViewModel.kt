package com.health13.yelpo.presentation.fragments.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.health13.yelpo.data.models.Category
import com.health13.yelpo.data.models.CategoryResponse
import com.health13.yelpo.data.models.YelpBusiness
import com.health13.yelpo.data.models.YelpSearchResult
import com.health13.yelpo.domain.GetBusinessByCategoryUseCase
import com.health13.yelpo.domain.GetCategoriesUseCase
import com.health13.yelpo.domain.GetRestaurantUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>()
    private val _yelpRestaurants = MutableLiveData<List<YelpBusiness>>()
    private val _categories = MutableLiveData<List<Category>>()
    private val _categoryBusinesses =  MutableLiveData<List<YelpBusiness>>()


    val yelpRestaurants : LiveData<List<YelpBusiness>> = _yelpRestaurants
    val categories : LiveData<List<Category>> = _categories
    val progressBar : LiveData<Boolean> = _progressBar
    val categoryBusinesses:LiveData<List<YelpBusiness>> = _categoryBusinesses

    private val getRestaurantUseCase = GetRestaurantUseCase()
    private val getCategoriesUseCase = GetCategoriesUseCase()
    private val getBusinessByCategoryUseCase = GetBusinessByCategoryUseCase()

    init {
        getRestaurants()
        getCategories()
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

                    this@HomeViewModel._categoryBusinesses.value = response.body()!!.restaurants
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

    private fun getRestaurants(){
        CoroutineScope(Dispatchers.IO).launch {
            _progressBar.postValue(true)
            getRestaurantUseCase.execute().enqueue(object :
                Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {

                    Log.i(ContentValues.TAG, "onResponse ${response.body()!!.restaurants}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(ContentValues.TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    }

                    _yelpRestaurants.value = response.body()!!.restaurants

                    _progressBar.postValue(false)
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(ContentValues.TAG, "onFailure $t")

                    _progressBar.postValue(false)
                }
            })

        }


    }
}