package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.app.model.journal.entities.DiaryUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment

interface DiaryRepository {

    suspend fun getDiary(
        startDate: String,
        endDate: String,
    ): DiaryUiEntity


    suspend fun getAssignment(classmeetingsId: List<Int>): List<Assignment>

    suspend fun getAttachments(assignmentId: Int): List<AttachmentEntity>
}
