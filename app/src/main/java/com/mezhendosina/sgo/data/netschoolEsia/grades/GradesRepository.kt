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

package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.app.uiEntities.GradesUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import kotlinx.coroutines.flow.StateFlow

interface GradesRepository {
    val grades: StateFlow<List<GradesUiEntity>>

    val years: StateFlow<List<SchoolYear>>
    val terms: StateFlow<List<Term>>

    val selectedYearId: StateFlow<Int>
    val selectedTrimId: StateFlow<Int>


    suspend fun initFilters()

    suspend fun getAboutGrade(subjectId: Int): SubjectPerformance

    suspend fun getGrades()

    suspend fun getSelectedYear(): Int?
    suspend fun setYearId(id: Int)
    suspend fun setTrimId(id: Int)

    suspend fun sortGrades()

}