package com.diplom.mediresult.domain.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ShopCart::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MainDb: RoomDatabase() {
    abstract val daoShop: DaoShop
    companion object{
        fun createDB(context: Context): MainDb{
            return Room.databaseBuilder(
                context = context,
                klass = MainDb::class.java,
                name = "ShopDB"
            ).build()
        }
    }
}