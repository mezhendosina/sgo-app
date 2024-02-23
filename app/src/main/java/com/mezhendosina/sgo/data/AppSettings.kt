package com.mezhendosina.sgo.data

import androidx.datastore.preferences.core.Preferences
import com.mezhendosina.sgo.data.netschool.api.login.LoginEntity
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

    suspend fun saveLogin(
        loginData: LoginEntity,
        loggedIn: Boolean = true,
    )

    suspend fun saveEsiaLogin(
        loginState: String,
        userId: String,
    )

    suspend fun logout()
}
