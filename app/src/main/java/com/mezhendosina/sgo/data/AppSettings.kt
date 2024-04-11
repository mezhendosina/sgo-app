package com.mezhendosina.sgo.data

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface AppSettings {
    fun <T> getValue(value: Preferences.Key<T>): Flow<T?>

    suspend fun <T> setValue(
        value: Preferences.Key<T>,
        key: T,
    )

    suspend fun getStudentId(): Int

    suspend fun saveToken(
        token: String,
        refreshToken: String,
    )

    suspend fun logout()
}
