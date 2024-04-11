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

import com.mezhendosina.sgo.data.netschoolEsia.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschoolEsia.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitGradesSource
    @Inject
    constructor(
        config: RetrofitConfig,
    ) : BaseRetrofitSource(config), GradesSource {
        private val gradesApi = config.baseRetrofit.create(GradesApi::class.java)

        override suspend fun getWhyTotalGrade(
            studentId: Int,
            subjectId: Int,
            termId: Int,
        ): SubjectPerformance =
            wrapRetrofitExceptions {
                gradesApi.getWhyTotalGrade(studentId, subjectId, termId)
            }

    override suspend fun getGrades(
            studentId: Int,
            yearId: Int,
        ): List<SubjectTotal> =
            wrapRetrofitExceptions {
                gradesApi.getGrades(studentId, yearId)
            }
}
