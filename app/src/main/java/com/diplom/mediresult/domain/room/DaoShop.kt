package com.diplom.mediresult.domain.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoShop {
    @Insert
    suspend fun insertItem(shopCart: ShopCart)

    @Delete
    suspend fun deleteItem(shopCart: ShopCart)

    @Query("SELECT * FROM ShopCart")
    suspend fun getShopsItems(): Flow<List<ShopCart>>

}