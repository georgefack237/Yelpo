package com.health13.yelpo.presentation.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health13.yelpo.data.models.Business
import com.health13.yelpo.domain.DeleteFavoriteUseCase
import com.health13.yelpo.domain.GetFavoriteStatusUseCase
import com.health13.yelpo.domain.SaveFavoriteBusinessUseCase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel() : ViewModel() {



    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    private val saveFavoriteBusinessUseCase = SaveFavoriteBusinessUseCase()
    private val deleteFavoriteUseCase = DeleteFavoriteUseCase()
    private val getFavoriteStatusUseCase = GetFavoriteStatusUseCase()


    init {

    }



    private fun checkFavoriteStatus(id:String) {

        CoroutineScope(Dispatchers.IO).launch {
            getFavoriteStatusUseCase.execute(id).asLiveData().apply {
                _isFavorite.postValue(this.value!!.isFavourite)
            }

        }
    }


    fun addFavourite(business: Business){

        business.isFavourite = !business.isFavourite

        if(business.isFavourite){

            CoroutineScope(Dispatchers.IO).launch {

            }

        }else{
           CoroutineScope(Dispatchers.IO).launch {

           }
        }

    }




}
