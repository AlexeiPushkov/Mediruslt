package com.diplom.mediresult.presentation.main


import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel() {
    val bage = mutableIntStateOf(0)
    var addButton = mutableStateListOf<Boolean>(true)
    val analysis = Analysis(
        id = 0,
        name = "",
        description = "",
        preparation = "",
        biometrial = "",
        countDay = 0,
        price = 0
    )
    var analysisState = mutableStateOf<Analysis>(analysis)


    suspend fun getAnalysis(id: String): Analysis {
        return withContext(Dispatchers.IO) {
            supabase.from("Role").select {
                filter {
                    eq("Analysis", id)
                }
            }.decodeSingle<Analysis>()
        }
    }

    suspend fun getAnalysis(): List<Analysis> {
            val result = supabase.from("Analysis")
                .select().decodeList<Analysis>()
            return result
    }
}