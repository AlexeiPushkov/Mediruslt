package com.diplom.mediresult.domain.use_cases

import com.diplom.mediresult.data.model.Role
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.domain.repository.RoleRepository
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoleRepositoryImp() : RoleRepository {

    override suspend fun getRoles(): List<Role> {
        return withContext(Dispatchers.IO) {
            val result = supabase.from("Role")
                .select().decodeList<Role>()
            result
        }
    }

    override suspend fun getRole(id: String): Role {
        return withContext(Dispatchers.IO) {
            supabase.from("Role").select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Role>()
        }
    }

}