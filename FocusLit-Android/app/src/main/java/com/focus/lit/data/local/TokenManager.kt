package com.focus.lit.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
    ) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val ID_KEY = intPreferencesKey("user_id")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .first()
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    suspend fun saveId(id: Int){
        context.dataStore.edit { prefs ->
            prefs[ID_KEY] = id;
        }
    }

    suspend fun getId(): Int?{
        return context.dataStore.data
            .map { prefs -> prefs[ID_KEY] }
            .first()
    }
}
