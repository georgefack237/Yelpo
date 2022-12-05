package com.health13.yelpo.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.health13.yelpo.data.models.Business

// Annotates class to be a Room Database with a table (entity) of the Movie class
@Database(entities = [Business::class], version = 1)
abstract class YelpDatabase : RoomDatabase() {
    abstract fun yelpDao(): YelpDao
}