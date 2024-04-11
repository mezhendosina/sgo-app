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

package com.mezhendosina.sgo.app.model.grades

import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions.GradeOptions
import kotlinx.coroutines.flow.StateFlow

interface GradesRepositoryInterface {
    val selectedGradesItem: StateFlow<GradesItem?>

    suspend fun loadGrades(
        gradeOptions: GradeOptions,
        termid: String,
        sortType: Int,
    )

    suspend fun loadGradesOptions(): GradeOptions

    fun setSelectedGradesItem(gradesItem: GradesItem)

    fun addListener(listener: GradeActionListener)

    fun removeListener(listener: GradeActionListener)
}
