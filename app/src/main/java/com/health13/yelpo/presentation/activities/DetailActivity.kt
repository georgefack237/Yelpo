package com.health13.yelpo.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.health13.yelpo.R
import com.health13.yelpo.presentation.viewmodels.RestaurantDetailViewModel
import com.health13.yelpo.utils.YELPConstants

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: RestaurantDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val  id = intent.getStringExtra(YELPConstants.INTENT_DETAIL_ID)

        detailViewModel = ViewModelProvider(this)[RestaurantDetailViewModel::class.java]

        detailViewModel.getRestaurant(id!!)

        val image: ImageView = findViewById(R.id.restImage)
        val name: TextView = findViewById(R.id.restName)
        val location: TextView = findViewById(R.id.restLocation)

        detailViewModel.restaurantLivData.observe(this){

            Glide.with(this).load(it.imageUrl).apply(RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(image)

            name.text = it.name
            location.text = it.location.address

        }


    }
}