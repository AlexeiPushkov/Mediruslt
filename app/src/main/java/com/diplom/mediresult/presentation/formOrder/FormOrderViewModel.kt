package com.diplom.mediresult.presentation.formOrder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.mediresult.data.model.Order
import com.diplom.mediresult.data.model.ShopCart
import kotlinx.coroutines.launch
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.presentation.main.MainViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

@RequiresApi(Build.VERSION_CODES.O)
class FormOrderViewModel: ViewModel() {

    var state by mutableStateOf(OrderFormState())
    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: OrderFormEvent) {
        when (event) {
            is OrderFormEvent.AddressChanged -> {
                state = state.copy(address = event.address)
            }
            is OrderFormEvent.CommentChanged -> {
                state = state.copy(comment = event.comment)
            }
            is OrderFormEvent.DateChange ->{
                state = state.copy(date = event.date)
            }
        }
    }
    fun doOrder(price: Int, address: String,date: String, comment: String, carts: SnapshotStateSet<ShopCart>): Boolean{
        var description = ""
        for (cart in carts){
            description += "${cart.name}, "
        }
        try {
            viewModelScope.launch {
                val user = supabase.auth.retrieveUserForCurrentSession()
                val userId = user.id
                val order = Order(
                    idUser = userId,
                    description =  description,
                    price = price,
                    address =  address,
                    date = date,
                    comment =  comment
                )
                supabase.from("Order").insert(order)
            }
            return true
        }catch (e: Exception){
            return false
        }
    }
}