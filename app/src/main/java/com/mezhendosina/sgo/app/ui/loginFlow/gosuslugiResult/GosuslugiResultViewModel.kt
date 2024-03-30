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

package com.mezhendosina.sgo.app.ui.loginFlow.gosuslugiResult

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.app.utils.toDescription
import com.mezhendosina.sgo.app.utils.toLiveData
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepository
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GosuslugiResultViewModel
@Inject
constructor(
    val loginRepository: com.mezhendosina.sgo.data.netschool.repo.LoginRepository,
    val utilsRepository: UtilsRepository,
    val settingsDataStore: SettingsDataStore
) : ViewModel() {
    private val _loggedIn = MutableLiveData<Boolean?>(null)
    val loggedIn = _loggedIn.toLiveData()

    private val _users = MutableLiveData<List<UserInfo>>()
    val users = _users.toLiveData()

    private val _error = MutableLiveData<String>()
    val error = _error.toLiveData()

    suspend fun login(code: Int) {
        try {
            loginRepository.login(code)
            val getUsers = utilsRepository.getUsers()
            if (getUsers.size == 1) {
                settingsDataStore.setValue(SettingsDataStore.CURRENT_USER_ID, getUsers[0].id)
            }
            withContext(Dispatchers.Main) {
                _users.value = getUsers
                _loggedIn.value = true
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _loggedIn.value = false
                _error.value = e.toDescription()
            }
        }
    }
}
