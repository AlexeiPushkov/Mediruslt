package com.diplom.mediresult

import android.app.Application
import com.diplom.mediresult.domain.room.MainDb

class NewsMediruslt: Application() {
    val database by lazy { MainDb.createDB(this) }
}