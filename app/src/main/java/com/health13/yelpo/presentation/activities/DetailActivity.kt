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
import com.health13.yelpo.databinding.ActivityDetailBinding
import com.health13.yelpo.databinding.ActivityMainBinding
import com.health13.yelpo.presentation.viewmodels.RestaurantDetailViewModel
import com.health13.yelpo.utils.YELPConstants

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: RestaurantDetailViewModel
    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val  id = intent.getStringExtra(YELPConstants.INTENT_DETAIL_ID)

        detailViewModel = ViewModelProvider(this)[RestaurantDetailViewModel::class.java]

        detailViewModel.getRestaurant(id!!)


        detailViewModel.restaurantLivData.observe(this){

            Glide.with(this).load(it.imageUrl).apply(RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.restImage)

            binding.restName.text = it.name
            binding.restLocation.text = it.location.address

        }


    }
}