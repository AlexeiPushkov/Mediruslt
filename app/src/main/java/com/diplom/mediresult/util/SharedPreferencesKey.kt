package com.diplom.mediresult.util

import android.content.Context
import androidx.core.content.edit
import com.diplom.mediresult.data.model.ShopCart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesKey(
    private val context: Context
) {

    companion object{
        private const val MY_PREF_KEY = "MY_PREF"
    }

    fun saveBadge(key: String, data: Int){
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit { putInt(key, data) }
    }

    fun getBadge(key: String): Int {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return if (sharedPreferences.getInt(key, 0) < 0)
            0
        else
            sharedPreferences.getInt(key, 0)
    }

    fun saveStringData(key: String, data: String){
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit { putString(key, data) }
    }

    fun getStringData(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveShopCart(key: String, data: Set<ShopCart>){
        val listShopCartGson = Gson().toJson(data)
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit { putString(key, listShopCartGson) }
    }

    fun getShopCarts(key: String): Set<ShopCart>{
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        val data =  sharedPreferences.getString(key, null)
        val type = object : TypeToken<Set<ShopCart>>() {}.type
        val listShopCart = Gson().fromJson<Set<ShopCart>>(data,type)
        return if (listShopCart.isNullOrEmpty()) setOf<ShopCart>() else listShopCart
    }
}