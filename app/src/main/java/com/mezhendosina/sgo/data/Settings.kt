/*
 * Copyright 2023 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mezhendosina.sgo.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.mezhendosina.sgo.app.BuildConfig
import com.mezhendosina.sgo.app.model.grades.GradeSortType
import com.mezhendosina.sgo.app.model.journal.DiaryStyle
import com.mezhendosina.sgo.app.model.login.LoginEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class SettingsLoginData(
    val schoolId: Int,
    val UN: String,
    val PW: String,
)

class Settings(val context: Context) {


    companion object {
        val REGION_URL = stringPreferencesKey("region")

        val LOGGED_IN = booleanPreferencesKey("logged_in")

        val LOGIN = stringPreferencesKey("login")
        val PASSWORD = stringPreferencesKey("password")

        val CURRENT_USER_ID = intPreferencesKey("current_user_id")
        val THEME = intPreferencesKey("theme")
        val CURRENT_TRIM_ID = stringPreferencesKey("current_trim_id")
        val LAST_VERSION_NUMBER = intPreferencesKey("last_version_number")
        val SHOW_UPDATE_DIALOG = booleanPreferencesKey("show_update_dialog")
        val SORT_GRADES_BY = intPreferencesKey("sort_grades_by")
        val DIARY_STYLE = stringPreferencesKey("diary_style")

        val SKIP_SUNDAY = booleanPreferencesKey("skip_sunday")

        val SCHOOL_ID = intPreferencesKey("scid")
    }

    val loggedIn = context.dataStore.data.map { it[LOGGED_IN] ?: false }
    val theme = context.dataStore.data.map {
        it[THEME] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    val currentUserId = context.dataStore.data.map { it[CURRENT_USER_ID] ?: 0 }
    val currentTrimId = CURRENT_TRIM_ID.getValue("")
    val regionUrl = REGION_URL.getValue("")
    val lastVersionNumber = LAST_VERSION_NUMBER.getValue(BuildConfig.VERSION_CODE)
    val showUpdateDialog = SHOW_UPDATE_DIALOG.getValue(false)
    val sortGradesBy = SORT_GRADES_BY.getValue(GradeSortType.BY_LESSON_NAME)
    val diaryStyle = DIARY_STYLE.getValue(DiaryStyle.AS_CARD)
    val skipSunday = SKIP_SUNDAY.getValue(true)

    private fun <T> Preferences.Key<T>.getValue(defaultValue: T): Flow<T> =
        context.dataStore.data.map { it[this] ?: defaultValue }

    suspend fun <T> editPreference(key: Preferences.Key<T>, value: T) = context.dataStore.edit {
        it[key] = value
    }

    suspend fun saveALl(loginData: LoginEntity, loggedId: Boolean = true) {
        context.dataStore.edit { prefs ->
            prefs[LOGGED_IN] = loggedId
            prefs[LOGIN] = loginData.login
            prefs[PASSWORD] = loginData.password
            prefs[SCHOOL_ID] = loginData.schoolId
        }
    }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[LOGGED_IN] = false
            prefs[LOGIN] = ""
            prefs[PASSWORD] = ""
            prefs[SCHOOL_ID] = 0
        }
    }

    suspend fun getLoginData(): SettingsLoginData {
        context.dataStore.data.first().let {
            return SettingsLoginData(
                it[SCHOOL_ID] ?: 0,
                it[LOGIN] ?: "",
                it[PASSWORD] ?: ""
            )
        }
    }
}