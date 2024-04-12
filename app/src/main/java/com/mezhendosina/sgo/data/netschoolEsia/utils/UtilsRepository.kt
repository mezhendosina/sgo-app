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

package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface UtilsRepository {
    val subjects: StateFlow<List<Subject>>

    /**
     * Получает все года, которые существуют у пользователя
     */
    suspend fun getYears(): List<SchoolYear>

    /**
     * Получает предметы в учебном году
     */
    suspend fun getSubjects(yearId: Int)

    /**
     *
     */
    suspend fun getSubjectById(id: Int, yearId: Int): Subject?

    suspend fun getUsers(): List<UserInfo>
    suspend fun getTerms(yearId: Int): List<Term>
}
