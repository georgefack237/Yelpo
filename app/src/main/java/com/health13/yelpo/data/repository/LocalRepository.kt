package com.health13.yelpo.data.repository

import com.health13.yelpo.data.models.Business
import com.health13.yelpo.data.repository.database.YelpRoomDatabase
import kotlinx.coroutines.flow.Flow

class LocalRepository() {

    private val movieDao by lazy { YelpRoomDatabase.getDatabase().yelpDao() }

    fun getFavoriteBusinesses() : Flow<List<Business>> {
        return movieDao.getBusinesses()
    }

    fun checkFavoriteStatus(businessId: String) : Flow<Business> {
        return movieDao.getBusiness(businessId)
    }


}