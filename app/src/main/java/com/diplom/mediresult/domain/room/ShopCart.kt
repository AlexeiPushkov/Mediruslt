package com.diplom.mediresult.domain.room

import androidx.room.Entity

@Entity
data class ShopCart(
    val name: String,
    val price: Int
)
