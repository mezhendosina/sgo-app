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

package com.mezhendosina.sgo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mezhendosina.sgo.app.utils.StudentNotFoundException
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
class SettingsDataStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) :
    AppSettings {
        companion object {
            val REGION_URL = stringPreferencesKey("region")

            val LOGGED_IN = booleanPreferencesKey("logged_in")

            val REFRESH_TOKEN = stringPreferencesKey("netschool_refresh_token")
            val TOKEN = stringPreferencesKey("netschool_token")

            val CURRENT_USER_ID = intPreferencesKey("current_user_id")

            val THEME = intPreferencesKey("theme")
            val LAST_VERSION_NUMBER = intPreferencesKey("last_version_number")
            val SHOW_UPDATE_DIALOG = booleanPreferencesKey("show_update_dialog")
            val SORT_GRADES_BY = intPreferencesKey("sort_grades_by")

            val DIARY_STYLE = stringPreferencesKey("diary_style")

            val SKIP_SUNDAY = booleanPreferencesKey("skip_sunday")

            val DOWNLOAD_ALL_FILES = booleanPreferencesKey("download_all_files")
        }

        override fun <T> getValue(value: Preferences.Key<T>): Flow<T?> {
            return context.dataStore.data.map { it[value] }
        }

        override suspend fun <T> setValue(
            value: Preferences.Key<T>,
            key: T,
        ) {
            context.dataStore.edit {
                it[value] = key
            }
        }

        override suspend fun getStudentId(): Int = getValue(CURRENT_USER_ID).first() ?: throw StudentNotFoundException()

        override suspend fun saveToken(
            token: String,
            refreshToken: String,
        ) {
            context.dataStore.edit { prefs ->
                prefs[TOKEN] = token
                prefs[REFRESH_TOKEN] = refreshToken
            }
        }



        override suspend fun logout() {
            context.dataStore.edit { prefs ->
                prefs[LOGGED_IN] = false
                prefs[REFRESH_TOKEN] = ""
            }
        }
    }
