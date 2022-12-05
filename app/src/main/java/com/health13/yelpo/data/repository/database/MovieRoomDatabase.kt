package com.health13.yelpo.data.repository.database

import android.content.Context
import androidx.room.Room

object YelpRoomDatabase {
    // Singleton prevents multiple instances of database opening at the same time.
    @Volatile
    private var database: YelpDatabase? = null

    fun getDatabase(context: Context? = null): YelpDatabase {
        val localDatabase = database
            // If database exists...
        return localDatabase
            ?: synchronized(this) {
                // If database does not exist...
                buildDatabase(context!!).also { database = it }
            }
    }

    private fun buildDatabase(context: Context): YelpDatabase {
        return Room.databaseBuilder(context, YelpDatabase::class.java, "yelp_db")
            .build()
    }
}