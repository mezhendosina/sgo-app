package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.data.netschool.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschool.base.RetrofitConfig
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
