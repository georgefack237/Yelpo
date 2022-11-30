package com.health13.yelpo.domain

import com.health13.yelpo.data.repository.RemoteRepository

class GetCategoriesUseCase(private val repository: RemoteRepository = RemoteRepository()) {
    fun execute() = repository.getCategories()
}