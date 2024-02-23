package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings.Classmeetings

interface DiaryRepository {
    suspend fun getDiary(
        startDate: String,
        endDate: String,
    ): List<Classmeetings>

    suspend fun getAssignment(classmeetingId: Int): List<Assignment>
}
