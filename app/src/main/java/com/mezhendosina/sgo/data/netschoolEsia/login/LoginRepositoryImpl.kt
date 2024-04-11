package com.mezhendosina.sgo.data.netschoolEsia.login

import android.content.Context
import com.mezhendosina.sgo.data.SettingsDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl
@Inject
constructor(
    val settingsDataStore: SettingsDataStore,
    val loginSource: LoginSource,
    @ApplicationContext val context: Context
) : LoginRepository {
    override suspend fun login(deviceCode: Int) {
        val loginResponse = loginSource.getToken(deviceCode)
        settingsDataStore.saveToken(
            loginResponse.accessToken,
            loginResponse.refreshToken,
        )
    }

    override suspend fun login() {
        val token = settingsDataStore.getValue(SettingsDataStore.REFRESH_TOKEN)
        val newTokens = token.first()?.let { loginSource.getToken(it) }
        newTokens?.let {
            settingsDataStore.saveToken(
                it.accessToken,
                it.refreshToken
            )
        }
    }

    override suspend fun logout() = Unit
}
