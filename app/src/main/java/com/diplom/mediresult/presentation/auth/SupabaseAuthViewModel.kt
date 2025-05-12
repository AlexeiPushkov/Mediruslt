package com.diplom.mediresult.presentation.auth

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.diplom.mediresult.data.model.UserState
import com.diplom.mediresult.data.network.SupabaseClient.supabase
import com.diplom.mediresult.presentation.auth.login.LoginFormEvent
import com.diplom.mediresult.presentation.auth.login.LoginFormState
import com.diplom.mediresult.presentation.auth.signup.SignUpFormEvent
import com.diplom.mediresult.presentation.auth.signup.SignUpFormState
import com.diplom.mediresult.presentation.nvgraph.Route
import com.diplom.mediresult.util.SharedPreferencesKey
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


@RequiresApi(Build.VERSION_CODES.O)
class SupabaseAuthViewModel(): ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState
    var state by mutableStateOf(LoginFormState())
    var signUpstate by mutableStateOf(SignUpFormState())
    fun signUp(
        navController: NavController,
        context: Context,
        userEmail: String,
        userPassword: String,
        fio: String,
        date: String,
        gender: String,
        pathImg: String?,
    ){
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        put("fio", fio)
                        put("date", date.toString())
                        put("gender", gender)
                        put("path_img",pathImg)
                        put("password", userPassword)
                    }
                }
                saveToken(context)
                _userState.value = UserState.Success("Успешная регистрация")
                supabase.auth.resendEmail(OtpType.Email.SIGNUP, userEmail)
                if (isUserLoggedIn(context = context,)){
                    navController.navigate(Route.MainScreen.route)
                }
            }catch (e: Exception){
                _userState.value = UserState.Error("Error: $e")
            }
        }
    }

    private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken = supabase.auth.currentAccessTokenOrNull() ?: ""
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferencesKey(context)
        return sharedPref.getStringData("accessToken")
    }
    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.EmailChanged -> {
                signUpstate = signUpstate.copy(email = event.email)
            }
            is SignUpFormEvent.PasswordChanged -> {
                signUpstate = signUpstate.copy(password = event.password)
            }
            is SignUpFormEvent.FioChange -> {
                signUpstate = signUpstate.copy(fio = event.fio)
            }
            is SignUpFormEvent.DateChange ->{
                signUpstate = signUpstate.copy(date = event.date)
            }
            is SignUpFormEvent.TermsChange -> {
                signUpstate = signUpstate.copy(acceptedTerms = event.terms)
            }
            is SignUpFormEvent.GenderChange -> {
                signUpstate = signUpstate.copy(gender = event.gender)
            }
        }
    }

    fun loginUser(
        navController: NavController,
        context: Context,
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                if (isUserLoggedIn(context = context,)){
                    navController.navigate(Route.MainScreen.route)
                }
            } catch (e: Exception) {
                if (e.message?.contains("Email not confirmed") == true) {
                    // If the email isn’t confirmed, trigger resend.
                    //supabase.auth.resendEmail(OtpType.Email.SIGNUP, userEmail)
                    saveToken(context)
                    if (isUserLoggedIn(context = context,)){
                        navController.navigate(Route.MainScreen.route)
                    }
                } else {
                    _userState.value = UserState.Error("Неправильная почта или пароль")
                }
            }
        }
    }



    fun logoutUser(
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                val sharedPref = SharedPreferencesKey(context)
                sharedPref.saveStringData("accessToken", "")
                navController.navigate(Route.LoginScreen.route)

            } catch (e: Exception) {
            }
        }
    }

    fun isUserLoggedIn(
        context: Context,
    ): Boolean {
        val token: String = getToken(context).toString()
        if (token.isEmpty()) {
            return false
        } else {
            viewModelScope.launch {
                try {
                    supabase.auth.retrieveUser(token)
                    supabase.auth.refreshCurrentSession()
                    saveToken(context)
                }catch (e: Exception) {
                }
            }
            return true
        }
    }
}