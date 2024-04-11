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
