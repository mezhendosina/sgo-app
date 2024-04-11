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

package com.mezhendosina.sgo.app.ui.gradesFlow.grades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.uiEntities.GradesUiEntity
import com.mezhendosina.sgo.app.utils.BaseViewModel
import com.mezhendosina.sgo.app.utils.LoadStates
import com.mezhendosina.sgo.app.utils.toDescription
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class GradesViewModel @Inject constructor(
    private val gradesRepository: GradesRepository,
) : BaseViewModel() {

    private val _grades = MutableLiveData<List<GradesUiEntity>>()
    val grades: LiveData<List<GradesUiEntity>> = _grades

    var gradeAdapter: GradeAdapter? = null

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun setAdapter(onClickListener: OnGradeClickListener) {
        gradeAdapter = GradeAdapter(onClickListener)
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            gradesRepository.initFilters()
            gradesRepository.getGrades()
        }
        viewModelScope.launch {
            gradesRepository.grades.collect { subjectTotals ->
                _grades.value = subjectTotals
            }
        }
    }

    suspend fun load() {
        try {
            gradesRepository.getGrades()
            withContext(Dispatchers.Main) {
                Singleton.updateGradeState.value = LoadStates.FINISHED
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e(null, e.stackTraceToString())
                _errorMessage.value = e.toDescription()
                Singleton.updateGradeState.value = LoadStates.ERROR
            }
        }
    }
}