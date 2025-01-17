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

package com.mezhendosina.sgo.app.ui.loginFlow.chooseUserId

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.app.activities.MainActivity
import com.mezhendosina.sgo.app.uiEntities.UserUIEntity
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseUserIdViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val utilsRepository: UtilsRepository
) : ViewModel() {

    private val _usersId = MutableLiveData<List<UserUIEntity>>()
    val usersId: LiveData<List<UserUIEntity>> = _usersId

    init {
        viewModelScope.launch {
            _usersId.value = utilsRepository.getUsers().map {
                UserUIEntity(
                    it.id,
                    null,
                    null,
                    it.nickName,
                    it.loginName
                )
            }
        }
    }


    fun login(context: Context, userUIEntity: UserUIEntity) {
        try {
            viewModelScope.launch {
                settingsDataStore.setValue(SettingsDataStore.CURRENT_USER_ID, userUIEntity.userId!!)
            }
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(context, intent, null)
        } catch (e: Exception) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

}
