package com.health13.yelpo.data.repository.database


import androidx.room.*
import com.health13.yelpo.data.models.Business
import kotlinx.coroutines.flow.Flow

@Dao
interface YelpDao {

    @Query("SELECT * from business ORDER BY name ASC")
    fun getBusinesses(): Flow<List<Business>>

    @Query("SELECT * from business WHERE id= :id")
    fun getBusiness(id: String): Flow<Business>

}
