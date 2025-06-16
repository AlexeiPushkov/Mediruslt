package com.diplom.mediresult.presentation.main


import android.content.Context
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.model.ShopCart
import com.diplom.mediresult.data.model.User
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.presentation.main.pages.profile.ProfileEvent
import com.diplom.mediresult.presentation.main.pages.profile.ProfileState
import com.diplom.mediresult.util.SharedPreferencesKey
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(): ViewModel() {
    val bage = mutableIntStateOf(0)
    val analysis = Analysis(
        id = 0,
        name = "",
        description = "",
        preparation = "",
        biometrial = "",
        countDay = 0,
        price = 0
    )
    var finalPrice =mutableIntStateOf(0)
    var analysisState = mutableStateOf<Analysis>(analysis)
    var shopCartState = mutableStateSetOf<ShopCart>()
    var mainIndex = mutableIntStateOf(0)
    var loading = mutableStateOf(false)
    var state by mutableStateOf(ProfileState())
    var validations = mutableStateOf(false)
    var validationsPhone = mutableStateOf(false)
    var success = mutableStateOf("")


    fun getPrice(carts:  SnapshotStateSet<ShopCart>){
        var price = 0
        for (cart in carts){
            price += cart.price
        }
        finalPrice.intValue = price
    }

    fun saveShopCarts(context: Context, data: Set<ShopCart>){
        viewModelScope.launch {
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.saveShopCart("shopCart", data)
        }
    }

    fun getShopCarts(context: Context) {
        val sharedPref = SharedPreferencesKey(context)
        val list = if (!sharedPref.getShopCarts("shopCart").isEmpty()) sharedPref.getShopCarts("shopCart") else listOf<ShopCart>()
        shopCartState.addAll(list)
    }

    fun saveBage(context: Context, data: Int){
        viewModelScope.launch {
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.saveBadge("badge", data)
        }
    }

    fun getBadge(context: Context) {
        val sharedPref = SharedPreferencesKey(context)
        val badge = sharedPref.getBadge("badge")
        bage.intValue = badge
    }

    suspend fun getAnalysis(): List<Analysis> {
        val result = supabase.from("Analysis")
            .select().decodeList<Analysis>()
        return result
    }

    suspend fun getAnalyses(id: Int): Analysis {
        val result = supabase.from("Analysis")
            .select{
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Analysis>()
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: ProfileEvent) {
        state = when (event) {
            is ProfileEvent.FioChange -> {
                state.copy(fio = event.fio)
            }

            is ProfileEvent.DateChange ->{
                state.copy(date = event.date)
            }

            is ProfileEvent.GenderChange -> {
                state.copy(gender = event.gender)
            }
            is ProfileEvent.PhoneChange -> {
                state.copy(phone = event.phone)
            }
        }
    }

    fun validationsFIO(){
        val invalidSymbolRegex = Regex("[^а-яА-Я\\s]")
        validations.value = invalidSymbolRegex.containsMatchIn(state.fio)
    }

    fun validationsPhone(){
        validations.value = !Patterns.PHONE.matcher(state.phone).matches()
    }

    fun formatPhoneNumber(phoneNumber: String): String {
        // Remove all non-digit characters
        val digits = phoneNumber.filter { it.isDigit() }

        // Apply formatting
        return buildString {
            for (i in digits.indices) {
                when (i) {
                    0 -> append('(')
                    3 -> append(") ")
                    6 -> append('-')
                }
                append(digits[i])
                if (i == 9) break // no more digits
            }
        }
    }

    suspend fun getUser(): User {
        val user = supabase.auth.retrieveUserForCurrentSession()
        val userId = user.id
        val result = supabase.from("profiles")
            .select{
                filter{
                    eq("id", userId)
                }
            }.decodeSingle<User>()
        return result
    }

    fun updateUserData(
        fio: String,
        date: String,
        gender: String,
        phone: String
    ) {
        viewModelScope.launch {
            val user = supabase.auth.retrieveUserForCurrentSession()
            val userId = user.id
            try {
                supabase.from("profiles").update({
                    set("fio", fio)
                    set("date", date)
                    set("gender", gender)
                    set("phone", phone)
                }) {
                    filter {
                        eq("id", userId)
                    }
                }
                success.value = "Данные успешно измененны"
                onEvent(ProfileEvent.DateChange(date))
            }
            catch (e: Exception){
                success.value = "Данные не измененны"
            }
        }
    }
}