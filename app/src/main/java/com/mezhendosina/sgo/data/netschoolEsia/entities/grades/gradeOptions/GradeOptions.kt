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

package com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity

data class GradeOptions(
    val PCLID: InputTag,
    val ReportType: List<SelectTag>,
    val SID: InputTag,
    val TERMID: List<SelectTag>,
) {
    fun getTerms(): List<FilterUiEntity> = TERMID.map { FilterUiEntity(it.value.toInt(), it.name, it.is_selected) }
}
