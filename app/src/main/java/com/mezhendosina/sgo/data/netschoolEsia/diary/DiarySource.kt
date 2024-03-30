package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings.Classmeetings

interface DiarySource {
    suspend fun getDiary(
        studentId: Int,
        startDate: String,
        endDate: String,
    ): List<Classmeetings>

    suspend fun getAssignments(
        studentId: Int,
        classmeetingId: Int,
    ): List<Assignment>

    suspend fun getAssignments(
        studentId: Int,
        classmeetingId: List<Int>,
    ): List<Assignment>

    suspend fun getAttachment(assignmentId: Int): List<AttachmentEntity>
}
