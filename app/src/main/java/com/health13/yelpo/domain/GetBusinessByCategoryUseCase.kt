package com.health13.yelpo.domain

import com.health13.yelpo.data.repository.RemoteRepository

class GetBusinessByCategoryUseCase(private val repository: RemoteRepository = RemoteRepository()) {
    fun execute(category: String) = repository.getBusinessByCategory(category)
}


