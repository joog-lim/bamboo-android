package com.example.data.utils


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.utils.Constant.Companion.DEFAULT_TOKEN
import com.example.data.utils.Constant.Companion.PREFERENCES_TOKEN
import com.example.data.utils.Constant.Companion.PREFERENCE_NAME
import com.example.data.utils.DataStoreRepository.PreferencesKeys.dataStoreToken

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {


    private object PreferencesKeys {
        val dataStoreToken = stringPreferencesKey(PREFERENCES_TOKEN)

    }

    // context.createDataStore 사라지고 globalDataStore 로 마이그레이션
    private val dataStore: DataStore<Preferences> =
        context.dataStore


    // 데이터를 쓴다
    suspend fun saveToken(token: String) {

        // 데이터 스트림에 내보낼 수 있는 새 흐름을 만듦
        dataStore.edit { preferences ->
            preferences[dataStoreToken] = token
            Log.d("DataStoreRepository", "saveToken: ${preferences[dataStoreToken]}")


        }


    }


    val readToken: Flow<Token> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val token = preferences[dataStoreToken] ?: DEFAULT_TOKEN
            Log.d("DataStoreRepository", "readToken  $token")
            Token(token)
        }


}




data class Token(
    val token: String,
)