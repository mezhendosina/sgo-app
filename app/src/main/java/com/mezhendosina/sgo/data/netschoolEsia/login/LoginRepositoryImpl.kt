/*
 * Copyright 2024 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mezhendosina.sgo.data.netschoolEsia.login

import android.content.Context
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.netschoolEsia.NetSchoolSingleton
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
    val calendarRepository: CalendarRepository,
    @ApplicationContext val context: Context
) : LoginRepository {
    override suspend fun login(deviceCode: Int) {
        val now = calendarRepository.getNow()
        val loginResponse = loginSource.getToken(deviceCode)
        settingsDataStore.saveToken(
            loginResponse.accessToken,
            loginResponse.refreshToken,
            now + loginResponse.expiresIn

        )
        NetSchoolSingleton.isLoggedIn(true)
    }

    override suspend fun login() {
        val timeToRefresh = settingsDataStore.getValue(SettingsDataStore.TIME_TO_REFRESH).first()
        val now = calendarRepository.getNow()
        if (timeToRefresh != null && timeToRefresh > now) {
            NetSchoolSingleton.isLoggedIn(true)
            return
        }
        val token = settingsDataStore.getValue(SettingsDataStore.REFRESH_TOKEN)

        val newTokens = token.first()?.let { loginSource.getToken(it) }
        newTokens?.let {
            settingsDataStore.saveToken(
                it.accessToken,
                it.refreshToken,
                now + newTokens.expiresIn
            )
        }
        NetSchoolSingleton.isLoggedIn(true)
    }
}
