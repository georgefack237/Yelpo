package com.health13.yelpo.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpBusiness>
)


@Entity
data class Business(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val rating: Double,
    val price: String,
    @ColumnInfo(name = "review_count")
    val numReviews: Int,
    @ColumnInfo(name ="distance")
    val distanceInMeters: Double,
    @ColumnInfo(name ="image_url")
    val imageUrl: String,
    var isFavourite: Boolean
)


data class YelpBusiness(
    val id: String,
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>,
    val location: YelpLocation,
    val region:Coordinates
) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}


data class Coordinates(
val center:Center
)


data class Center(
    val latitude: Double,
    val longitude: Double
)

data class YelpCategory(
    val title: String
)

data class YelpLocation(
    @SerializedName("address1") val address: String
)