package com.diplom.mediresult.presentation.main.pages.support

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.mediresult.data.model.Support
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    val supports = mutableStateListOf<Support>()

    fun createMessage(message: Support) {
        viewModelScope.launch {
            supabase.from("Support").insert(message)
        }
    }

    suspend fun getMessage(): List<Support> {
        try {

            val user = supabase.auth.retrieveUserForCurrentSession()
            val userId = user.id
            val messages = supabase.from("Support").select {

            }.decodeList<Support>()
            return messages
        } catch (e: Exception) {
            return listOf<Support>()
        }
    }


    fun realTime(scope: CoroutineScope){
        viewModelScope.launch {
            try{
                val channel = supabase.channel("Support")
                val dataFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public")

                dataFlow.onEach {
                    when(it){
                        is PostgresAction.Insert -> {
                            getMessage()
                        }
                        is PostgresAction.Delete -> {}
                        is PostgresAction.Select -> {}
                        is PostgresAction.Update -> {}
                    }
                }.launchIn(scope)
                channel.subscribe()
            }catch (e: Exception){

            }
        }
    }
}