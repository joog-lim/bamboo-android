package com.study.bamboo.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.study.bamboo.utils.Util.Companion.DEFAULT_TOKEN
import com.study.bamboo.utils.Util.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

    private object PreferenceKeys {
        val token = stringPreferencesKey(DEFAULT_TOKEN)

    }


    // context.createDataStore 사라지고 globalDataStore 로 마이그레이션
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> =
        context.dataStore


    // 데이터 저장
    suspend fun saveToken(
        token: String
    ) {

        // 데이터 스트림에 내보낼 수 있는 새 흐름을 만듦
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.token] = token
            Log.d("saveToken", "token: ${preferences[PreferenceKeys.token]}")


        }

    }

    //dataStore은 sharePrefernce와 다르게 코루틴이 가능하다
    // flow로 감싼다. (비동기 처리)
    val readToken: Flow<Token> = dataStore.data
        .catch { exception ->
            if (exception is Exception) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val token = preferences[PreferenceKeys.token] ?: DEFAULT_TOKEN
            Log.d("DataStoreRepository", "DataStoreRepository  $token")
            Token(
                token = token
            )
        }


}

data class Token(
    val token: String,
)