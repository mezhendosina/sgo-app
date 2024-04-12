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

import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilsRepositoryImpl
@Inject
constructor(
    private val utilsSource: UtilsSource,
    private val appSettings: AppSettings,
) : UtilsRepository {
    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    override val subjects: StateFlow<List<Subject>> = _subjects


    override suspend fun getYears(): List<SchoolYear> {
        val studentId = appSettings.getStudentId()
        return utilsSource.getYears(studentId).map {
            SchoolYear(it.endDate, it.id, it.name + " год", it.startDate)
        }
    }

    override suspend fun getSubjects(yearId: Int) {
        val studentId = appSettings.getStudentId()

        val subjects = utilsSource.getSubjects(
            studentId,
            yearId,
        )
        _subjects.emit(subjects)
    }

    override suspend fun getSubjectById(id: Int, yearId: Int): Subject? {
        if (subjects.value.isEmpty()) getSubjects(yearId)
        return subjects.value.firstOrNull { id == it.id }
    }

    override suspend fun getTerms(yearId: Int): List<Term> {
        val studentId = appSettings.getStudentId()
        return utilsSource.getTerms(studentId, yearId)

    }


    override suspend fun getUsers(): List<UserInfo> {
        return utilsSource.getUsers()
    }

}
