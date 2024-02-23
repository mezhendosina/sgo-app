package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings.Classmeetings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl
    @Inject
    constructor(
        val appSettings: AppSettings,
        val diarySource: DiarySource,
    ) : DiaryRepository {
        override suspend fun getDiary(
            startDate: String,
            endDate: String,
        ): List<Classmeetings> {
            val studentId = appSettings.getStudentId()
            return diarySource.getDiary(studentId, startDate, endDate)
        }

        override suspend fun getAssignment(classmeetingId: Int): List<Assignment> {
            val studentId = appSettings.getStudentId()
            return diarySource.getAssignments(studentId, classmeetingId)
        }
    }
