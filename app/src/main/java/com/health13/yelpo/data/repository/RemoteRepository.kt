package com.health13.yelpo.data.repository

import com.health13.yelpo.data.models.CategoryResponse
import com.health13.yelpo.data.models.YelpBusiness
import com.health13.yelpo.data.models.YelpSearchResult
import com.health13.yelpo.data.repository.network.NetworkInstance
import com.health13.yelpo.utils.YELPConstants
import retrofit2.Call

class RemoteRepository(private val network: NetworkInstance = NetworkInstance ) {

    fun getRestaurants(): Call<YelpSearchResult> {
        return network.api.searchRestaurants("Bearer ${YELPConstants.API_KEY}", "Real Estate","New York")
    }

    fun getBusinessByCategory(category: String): Call<YelpSearchResult> {
        return network.api.searchRestaurants("Bearer ${YELPConstants.API_KEY}", "$category","New York")
    }

    fun searchBusiness(query: String): Call<YelpSearchResult> {
        return network.api.searchRestaurants("Bearer ${YELPConstants.API_KEY}", "$query","New York")
    }

    fun getRestaurant(id: String): Call<YelpBusiness> {
        return network.api.getRestaurant("Bearer ${YELPConstants.API_KEY}",id)
    }

    fun getCategories(): Call<CategoryResponse>{
        return network.api.getCategories("Bearer ${YELPConstants.API_KEY}")
    }

}


