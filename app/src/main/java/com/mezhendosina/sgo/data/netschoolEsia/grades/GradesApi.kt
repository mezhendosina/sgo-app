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
