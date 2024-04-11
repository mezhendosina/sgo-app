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

package com.mezhendosina.sgo.app.ui.gradesFlow.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.model.grades.GradeSortType
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.app.uiEntities.checkItem
import com.mezhendosina.sgo.app.utils.BaseViewModel
import com.mezhendosina.sgo.app.utils.LoadStates
import com.mezhendosina.sgo.app.utils.toLiveData
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.netschoolEsia.base.toDescription
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepository
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GradesFilterViewModel
@Inject
constructor(
    private val settingsDataStore: SettingsDataStore,
    private val gradesRepository: GradesRepository,
) : BaseViewModel() {
    private val _gradesSortType: MutableLiveData<Int> = MutableLiveData()
    val gradesSortType = _gradesSortType.toLiveData()

    private val _years = MutableLiveData<List<FilterUiEntity>>()
    val years = _years.toLiveData()

    private val _trim = MutableLiveData<List<FilterUiEntity>>()
    val trim = _trim.toLiveData()


    private val _currentYearId = MutableLiveData<Int>()
    val currentYearId = _currentYearId.toLiveData()

    private val _currentTrimId = MutableLiveData<Int>()
    val currentTrimID = _currentTrimId.toLiveData()


    private val _errorDescription = MutableLiveData<String>()
    val errorDescription = _errorDescription.toLiveData()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading.toLiveData()

    init {
        viewModelScope.launch {
            gradesRepository.years.collect { schoolYears ->
                val selectedYear = gradesRepository.getSelectedYear() ?: return@collect
                val filters = schoolYears.map {
                    FilterUiEntity(
                        it.id,
                        it.name,
                        selectedYear == it.id
                    )
                }
                _years.value = filters
                _currentYearId.value = selectedYear
            }
        }
        viewModelScope.launch {
            gradesRepository.terms.collect { terms ->
                if (terms.isEmpty()) return@collect

                val selectedTrim = gradesRepository.selectedTrimId.value
                val filters = terms.map {
                    FilterUiEntity(
                        it.id,
                        it.name,
                        it.id == selectedTrim
                    )
                }
                _currentTrimId.value = selectedTrim
                _trim.value = filters
            }
        }
    }

    fun setGradeSort(sortBy: Int) {
        viewModelScope.launch {
            settingsDataStore.setValue(SettingsDataStore.SORT_GRADES_BY, sortBy)
            gradesRepository.sortGrades()
            _gradesSortType.value = sortBy
        }
    }


    suspend fun updateYear(yearId: Int) {
        try {
            gradesRepository.setYearId(yearId)
            withContext(Dispatchers.Main) {
                _currentYearId.value = yearId
                _years.value = _years.value?.checkItem(yearId)
                _states.value = LoadStates.UPDATE
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _errorDescription.value = e.toDescription()
            }
        }
    }

    fun getGradeSort() {
        viewModelScope.launch {
            _gradesSortType.value =
                settingsDataStore.getValue(SettingsDataStore.SORT_GRADES_BY)
                    .first() ?: GradeSortType.BY_LESSON_NAME
        }
    }

    fun changeTrimId(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                gradesRepository.setTrimId(id)
            }
            _currentTrimId.value = id
            _trim.value = _trim.value?.checkItem(id)
            _states.value = LoadStates.UPDATE
        }
    }

    suspend fun getSelectedGradeSort(): Int {
        return settingsDataStore.getValue(
            SettingsDataStore.SORT_GRADES_BY
        ).first() ?: GradeSortType.BY_LESSON_NAME

    }
}
