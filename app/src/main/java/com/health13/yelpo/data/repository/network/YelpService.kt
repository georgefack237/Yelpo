package com.health13.yelpo.data.repository.network

import com.health13.yelpo.data.models.CategoryResponse
import com.health13.yelpo.data.models.YelpBusiness
import com.health13.yelpo.data.models.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {


    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String) : Call<YelpSearchResult>


    @GET("businesses/{id}")
    fun getRestaurant(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Call<YelpBusiness>


    @GET("categories")
    fun getCategories(
        @Header("Authorization") authHeader: String
    ): Call<CategoryResponse>
}