package com.diplom.mediresult.presentation.main.pages.resultats

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.diplom.mediresult.data.model.Result
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class ResultsViewModel: ViewModel() {
    val results = mutableStateListOf<Result>()
    suspend fun getResults(): List<Result> {
        try {
            val user = supabase.auth.retrieveUserForCurrentSession()
            val userId = user.id
            val result = supabase.from("Result").select{
                filter {
                    eq("Id_user", userId)
                }
            }.decodeList<Result>()
            return result
        } catch (e: Exception) {
            return listOf()
        }
    }
}