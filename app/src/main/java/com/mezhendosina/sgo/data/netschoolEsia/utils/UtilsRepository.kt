package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import kotlinx.coroutines.flow.StateFlow

interface UtilsRepository {
    val years: StateFlow<List<SchoolYear>>
    val subjects: StateFlow<List<Subject>>

    suspend fun getYears()

    suspend fun getSubjects()

    suspend fun getCurrentYear(): SchoolYear

    suspend fun setCurrentYear(yearId: Int)
}
