package com.health13.yelpo.presentation.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.health13.yelpo.data.models.YelpRestaurant
import com.health13.yelpo.domain.RestaurantDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetailViewModel(): ViewModel() {

    private var _restaurantLiveData = MutableLiveData<YelpRestaurant>()

    val restaurantLivData: LiveData<YelpRestaurant> = _restaurantLiveData
    private val restaurantDetailsUseCase = RestaurantDetailsUseCase()

     fun getRestaurant(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            restaurantDetailsUseCase.execute(id).enqueue(object :
                Callback<YelpRestaurant> {
                override fun onResponse(call: Call<YelpRestaurant>, response: Response<YelpRestaurant>) {
                    Log.i(ContentValues.TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(ContentValues.TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    }

                    _restaurantLiveData.value = response.body()
                }

                override fun onFailure(call: Call<YelpRestaurant>, t: Throwable) {
                    Log.i(ContentValues.TAG, "onFailure $t")
                }
            })

        }
    }
}