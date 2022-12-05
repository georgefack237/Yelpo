package com.health13.yelpo.domain


import com.health13.yelpo.data.repository.LocalRepository


class  GetFavoriteStatusUseCase(private val localRepository: LocalRepository = LocalRepository())  {
  fun execute(id:String) = localRepository.checkFavoriteStatus(id)
}

