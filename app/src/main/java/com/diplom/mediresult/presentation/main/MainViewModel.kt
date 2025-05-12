package com.diplom.mediresult.presentation.main


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.model.ShopCart
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.util.SharedPreferencesKey
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch


class MainViewModel(): ViewModel() {
    val bage = mutableIntStateOf(0)
    val analysis = Analysis(
        id = 0,
        name = "",
        description = "",
        preparation = "",
        biometrial = "",
        countDay = 0,
        price = 0
    )
    var finalPrice =mutableIntStateOf(0)
    var analysisState = mutableStateOf<Analysis>(analysis)
    var shopCartState = mutableStateSetOf<ShopCart>()
    var mainIndex = mutableIntStateOf(0)


    fun getPrice(carts:  SnapshotStateSet<ShopCart>){
        var price = 0
        for (cart in carts){
            price += cart.price
        }
        finalPrice.intValue = price
    }

    fun saveShopCarts(context: Context, data: Set<ShopCart>){
        viewModelScope.launch {
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.saveShopCart("shopCart", data)
        }
    }

    fun getShopCarts(context: Context) {
        val sharedPref = SharedPreferencesKey(context)
        val list = if (!sharedPref.getShopCarts("shopCart").isEmpty()) sharedPref.getShopCarts("shopCart") else listOf<ShopCart>()
        shopCartState.addAll(list)
    }

    fun saveBage(context: Context, data: Int){
        viewModelScope.launch {
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.saveBadge("badge", data)
        }
    }

    fun getBadge(context: Context) {
        val sharedPref = SharedPreferencesKey(context)
        val badge = sharedPref.getBadge("badge")
        bage.intValue = badge
    }

    suspend fun getAnalysis(): List<Analysis> {
            val result = supabase.from("Analysis")
                .select().decodeList<Analysis>()
            return result
    }
}