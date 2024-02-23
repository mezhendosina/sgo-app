package com.mezhendosina.sgo.data.netschoolEsia.login

import com.mezhendosina.sgo.data.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl
    @Inject
    constructor(
        val settingsDataStore: SettingsDataStore,
        val loginSource: LoginSource,
    ) : LoginRepository {
        private val _users = MutableStateFlow<List<Int>>(emptyList())
        override val users: StateFlow<List<Int>> = _users

        override suspend fun login(deviceCode: Int) {
            val loginResponse = loginSource.getToken(deviceCode)
            settingsDataStore.saveToken(
                loginResponse.accessToken,
                loginResponse.refreshToken,
            )
        }

        override suspend fun login() {
            val token = settingsDataStore.getValue(SettingsDataStore.TOKEN)
            token.first()?.let { loginSource.getToken(it) }
        }

        override suspend fun getUsers() {
            val response = loginSource.getUsers().map { it.userId }
            _users.emit(response)
        }

        override suspend fun logout() {
//            TODO("Not yet implemented")
        }
    }
