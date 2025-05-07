package com.diplom.mediresult.presentation.main.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.diplom.mediresult.NewsMediruslt
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.domain.room.MainDb
import com.diplom.mediresult.domain.room.ShopCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class ShopCartViewModel(val database: MainDb): ViewModel() {



    fun insertItem(
        analysis: Analysis
    ){
        viewModelScope.launch {
            val item = ShopCart(name = analysis.name, price = analysis.price)

            database.daoShop.insertItem(shopCart = item)
        }
    }

    fun deleteItem(
        analysis: Analysis
    ){
        viewModelScope.launch {
            val item = ShopCart(name = analysis.name, price = analysis.price)

            database.daoShop.deleteItem(shopCart = item)
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = checkNotNull(extras[APPLICATION_KEY] as NewsMediruslt).database
                return ShopCartViewModel(database) as T
            }
        }
    }
}