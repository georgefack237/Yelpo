package com.health13.yelpo.presentation.adapters
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
import com.health13.yelpo.R
import com.health13.yelpo.data.models.YelpBusiness
import com.health13.yelpo.presentation.activities.DetailActivity
import com.health13.yelpo.utils.YELPConstants


class RestaurantsAdapter(val context: Context, var restaurants:List<YelpBusiness> = mutableListOf()) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business, parent, false))
    }

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val name: TextView = itemView.findViewById(R.id.tvBusinessName)
        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.businessRating)
        private val imageView: ImageView = itemView.findViewById(R.id.imgBusiness)

        fun bind(restaurant: YelpBusiness) {
            name.text = restaurant.name
            ratingBar.rating = restaurant.rating.toFloat()
            tvAddress.text = restaurant.location.address
            tvDistance.text = restaurant.displayDistance()
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




