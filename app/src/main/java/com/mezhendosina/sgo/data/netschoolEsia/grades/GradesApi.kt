package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal
import retrofit2.http.GET
import retrofit2.http.Query

interface GradesApi {
    @GET("api/mobile/analytics/subject-performance")
    suspend fun getWhyTotalGrade(
        @Query("studentId") studentId: Int,
        @Query("subjectId") subjectId: Int,
        @Query("termId") termId: Int,
    ): SubjectPerformance

    @GET("/api/mobile/totals")
    suspend fun getGrades(
        @Query("studentId") studentId: Int,
        @Query("schoolYearId") yearId: Int,
    ): List<SubjectTotal>
}
