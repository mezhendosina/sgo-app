package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.app.model.journal.entities.DiaryUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.WeekDayUiEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
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
        ): DiaryUiEntity {
            val studentId = appSettings.getStudentId()
            val getDiary = diarySource.getDiary(studentId, startDate, endDate)
            val weekDays = mutableListOf<WeekDayUiEntity>()

            return DiaryUiEntity(
                weekDays = weekDays,
                weekStart = startDate,
                weekEnd = endDate,
                pastMandatory = emptyList(),
            )
        }

        override suspend fun getAssignment(classmeetingId: Int): List<Assignment> {
            val studentId = appSettings.getStudentId()
            return diarySource.getAssignments(studentId, classmeetingId)
        }

        override suspend fun getAttachments(assignmentId: Int): List<AttachmentEntity> {
            return diarySource.getAttachment(assignmentId)
        }
    }
