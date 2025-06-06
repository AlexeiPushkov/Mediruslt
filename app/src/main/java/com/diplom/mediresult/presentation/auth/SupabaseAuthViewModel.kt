package com.diplom.mediresult.presentation.auth

import android.content.Context
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
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
    var validations = mutableStateListOf(false, false, false, false)

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
                // initialize Argon2Kt and load the native library
                val argon2Kt = Argon2Kt()
                val salt = "234qWerAr124312sfMwe23r"
                // hash a password
                val hashResult : Argon2KtResult = argon2Kt.hash(
                    mode = Argon2Mode.ARGON2_I,
                    password = userPassword.toByteArray(),
                    salt =  salt.toByteArray(),
                    tCostInIterations = 5,
                    mCostInKibibyte = 65536
                )
                val pass = hashResult.rawHashAsHexadecimal()
                supabase.auth.signUpWith(Email){
                    email = userEmail
                    password = pass
                    data = buildJsonObject {
                        put("fio", fio)
                        put("date", date.toString())
                        put("gender", gender)
                        put("path_img",pathImg)
                        put("password", pass)
                    }
                }
                saveToken(context)
                _userState.value = UserState.Success("Успешная регистрация")
                supabase.auth.resendEmail(OtpType.Email.SIGNUP, userEmail)
                if (isUserLoggedIn(context = context)){
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
        state = when (event) {
            is LoginFormEvent.EmailChanged -> {
                state.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                state.copy(password = event.password)
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
                val argon2Kt = Argon2Kt()
                val salt = "234qWerAr124312sfMwe23r"
                // hash a password
                val hashResult : Argon2KtResult = argon2Kt.hash(
                    mode = Argon2Mode.ARGON2_I,
                    password = userPassword.toByteArray(),
                    salt =  salt.toByteArray(),
                    tCostInIterations = 5,
                    mCostInKibibyte = 65536
                )
                val pass = hashResult.rawHashAsHexadecimal()
                supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = pass
                }
                saveToken(context)
                if (isUserLoggedIn(context = context)){
                    navController.navigate(Route.MainScreen.route)
                }
            } catch (e: Exception) {
                if (e.message?.contains("Email not confirmed") == true) {
                    // If the email isn’t confirmed, trigger resend.
                    //supabase.auth.resendEmail(OtpType.Email.SIGNUP, userEmail)
                    saveToken(context)
                    if (isUserLoggedIn(context = context)){
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

    fun validationsFIO(){
        val invalidSymbolRegex = Regex("[^а-яА-Я\\s]")
        validations[0] = invalidSymbolRegex.containsMatchIn(signUpstate.fio)
    }

    fun validationsEmail(){
        validations[1] = !Patterns.EMAIL_ADDRESS.matcher(signUpstate.email).matches()
    }

    fun validationsPassword(){
        validations[2] = signUpstate.password.length < 5
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