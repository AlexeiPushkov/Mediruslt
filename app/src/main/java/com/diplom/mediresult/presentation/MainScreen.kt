package com.diplom.mediresult.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.diplom.mediresult.data.model.Role
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainScreen() {
    var roles = remember {
        listOf<Role>()
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            roles = supabase.from("Role").select().decodeList<Role>()
        }
    }
    Text(
        text = "True"
    )
    LazyColumn {
        items(roles){role ->
            ListItem(
                headlineContent = {
                    Text(
                        text = "${role.id}  ${role.name}"
                    )
                }
            )
        }
    }
}
