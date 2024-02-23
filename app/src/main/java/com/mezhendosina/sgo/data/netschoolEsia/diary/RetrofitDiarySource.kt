package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.data.netschool.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschool.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings.Classmeetings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitDiarySource
    @Inject
    constructor(
        config: RetrofitConfig,
    ) : BaseRetrofitSource(config), DiarySource {
        private val diaryApi = config.baseRetrofit.create(DiaryApi::class.java)

        override suspend fun getDiary(
            studentId: Int,
            startDate: String,
            endDate: String,
        ): List<Classmeetings> =
            wrapRetrofitExceptions {
                diaryApi.getDiary(studentId, startDate, endDate)
            }

        override suspend fun getAssignments(
            studentId: Int,
            classmeetingId: Int,
        ): List<Assignment> =
            wrapRetrofitExceptions {
                diaryApi.getAssignments(studentId, classmeetingId)
            }
    }
