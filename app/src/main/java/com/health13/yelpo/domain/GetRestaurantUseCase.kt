package com.health13.yelpo.domain

import com.health13.yelpo.data.repository.RemoteRepository
import com.health13.yelpo.data.repository.network.NetworkInstance

class GetRestaurantUseCase(private val repository: RemoteRepository = RemoteRepository()) {
    fun execute() = repository.getRestaurants()
}









