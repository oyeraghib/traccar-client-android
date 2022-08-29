package org.traccar.client.login

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = "user_preference")

    //Reading authentication
    val authToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[AUTH_KEY]
        }

    //Saving email for authentication
    suspend fun saveAuth(auth: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_KEY] = auth
        }
    }

    companion object {
        private val AUTH_KEY = stringPreferencesKey("key_email")
    }

}