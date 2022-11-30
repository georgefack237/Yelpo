package com.health13.yelpo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.health13.yelpo.data.models.YelpRestaurant
import com.health13.yelpo.presentation.activities.DetailActivity
import com.health13.yelpo.utils.YELPConstants


class RestaurantsAdapter(val context: Context, var restaurants:List<YelpRestaurant> = mutableListOf()) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false))
    }

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val tvNumReviews: TextView = itemView.findViewById(R.id.tvNumReviews)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(restaurant: YelpRestaurant) {
            name.text = restaurant.name
            ratingBar.rating = restaurant.rating.toFloat()
            tvNumReviews.text = "${restaurant.numReviews} Reviews"
            tvAddress.text = restaurant.location.address
            tvCategory.text = restaurant.categories[0].title
            tvDistance.text = restaurant.displayDistance()
            tvPrice.text = restaurant.price
            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(imageView)


            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(YELPConstants.INTENT_DETAIL_ID, restaurant.id)
                context.startActivity(intent)

            }
        }
    }
}




