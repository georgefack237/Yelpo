package com.health13.yelpo.domain

import com.health13.yelpo.data.repository.RemoteRepository

class SearchBusinessUseCase(private val repository: RemoteRepository = RemoteRepository()) {
    fun execute(query: String) = repository.searchBusiness(query)
}

