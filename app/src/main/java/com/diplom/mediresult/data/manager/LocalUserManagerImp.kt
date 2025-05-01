package com.diplom.mediresult.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.diplom.mediresult.domain.manager.LocalUserManager
import com.diplom.mediresult.util.Constants
import com.diplom.mediresult.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

class LocalUserManagerImp(
    val context: Context
): LocalUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { setting->
            setting[PreferencesKey.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {preferences ->
            preferences[PreferencesKey.APP_ENTRY] == false

        }
    }

}



private object PreferencesKey{
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}