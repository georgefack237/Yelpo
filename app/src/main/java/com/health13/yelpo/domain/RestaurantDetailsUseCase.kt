package com.health13.yelpo.domain

import com.health13.yelpo.data.repository.RemoteRepository

class RestaurantDetailsUseCase(private val repository: RemoteRepository = RemoteRepository()) {
    fun execute(id:String) = repository.getRestaurant(id)
}