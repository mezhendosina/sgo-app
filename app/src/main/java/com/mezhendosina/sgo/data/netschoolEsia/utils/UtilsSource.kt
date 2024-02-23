package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear

interface UtilsSource {
    suspend fun getYears(studentId: Int): List<SchoolYear>

    suspend fun getSubjects(
        studentId: Int,
        yearId: Int,
    ): List<Subject>
}
