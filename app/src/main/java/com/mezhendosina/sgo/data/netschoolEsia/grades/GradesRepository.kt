package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal
import kotlinx.coroutines.flow.StateFlow

interface GradesRepository {
    val grades: StateFlow<List<SubjectTotal>>

    suspend fun getGrades()

    suspend fun getAboutGrade(
        subjectId: Int,
        trimId: Int,
    ): SubjectPerformance
}
