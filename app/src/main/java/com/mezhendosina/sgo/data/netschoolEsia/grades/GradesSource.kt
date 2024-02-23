package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal

interface GradesSource {
    suspend fun getWhyTotalGrade(
        studentId: Int,
        subjectId: Int,
        termId: Int,
    ): SubjectPerformance

    suspend fun getGrades(
        studentId: Int,
        yearId: Int,
    ): List<SubjectTotal>
}
