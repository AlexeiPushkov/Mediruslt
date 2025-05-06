package com.diplom.mediresult.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.model.Role
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.domain.repository.RoleRepository
import com.diplom.mediresult.domain.use_cases.RoleRepositoryImp
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel() {
    private val _roleList = MutableStateFlow<List<Analysis>>(listOf())
    val roleList: Flow<List<Analysis>> = _roleList



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
        return withContext(Dispatchers.IO) {
            val result = supabase.from("Analysis")
                .select().decodeList<Analysis>()
            result
        }
    }
}