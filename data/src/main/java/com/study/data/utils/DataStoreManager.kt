package com.study.data.utils


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.study.data.utils.DataStoreManager.Companion.PREFERENCE_NAME
import com.study.data.utils.DataStoreManager.PreferencesKeys.dataStoreToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {


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
    suspend fun deleteToken(token:String){
        dataStore.edit {preference->
            preference.clear()
        }

    }


    var readToken: Flow<Token> = dataStore.data
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

    suspend fun getToken(): String {
        return readToken.first().token
    }

    companion object {
        const val DEFAULT_TOKEN = "default token"
        const val PREFERENCES_TOKEN = "preferences token"
        const val PREFERENCE_NAME = "bamboo token"
    }

}


data class Token(
    val token: String,
)