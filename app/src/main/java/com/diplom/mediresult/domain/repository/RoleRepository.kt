package com.diplom.mediresult.domain.repository

import com.diplom.mediresult.data.model.Role

interface RoleRepository {
    suspend fun getRoles(): List<Role>?
    suspend fun getRole(id: String): Role
}