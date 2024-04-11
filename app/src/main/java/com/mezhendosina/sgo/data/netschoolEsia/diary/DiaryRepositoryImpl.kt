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

package com.mezhendosina.sgo.data.netschoolEsia.diary

import android.content.Context
import com.mezhendosina.sgo.app.model.journal.entities.DiaryUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.LessonUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.WeekDayUiEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.dateToRussian
import com.mezhendosina.sgo.data.dateToTime
import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl
@Inject
constructor(
    val appSettings: AppSettings,
    val diarySource: DiarySource,
    @ApplicationContext val context: Context
) : DiaryRepository {


    override suspend fun getDiary(
        startDate: String,
        endDate: String,
    ): DiaryUiEntity {
        val studentId = appSettings.getStudentId()
        val getDiary = diarySource.getDiary(studentId, startDate, endDate)
        val weekDays: HashMap<String, MutableList<LessonUiEntity>> = hashMapOf()
        getDiary.forEach { classmeeting ->
            val weekDay = weekDays.getOrDefault(classmeeting.day, mutableListOf())

            weekDay.add(
                LessonUiEntity(
                    emptyList(),
                    null,
                    classmeeting.classmeetingId,
                    classmeeting.day,
                    dateToTime(classmeeting.endTime),
                    false,
                    classmeeting.order,
                    classmeeting.scheduleTimeRelay,
                    dateToTime(classmeeting.startTime),
                    classmeeting.subjectName
                )
            )
            weekDays[classmeeting.day] = weekDay
        }

        val out = weekDays.map { entry ->
            WeekDayUiEntity(
                entry.key, entry.value.sortedBy { it.number }.toMutableList()
            )
        }
        val sortedOut = out.sortedBy { it.date }

        return DiaryUiEntity(
            weekDays = sortedOut,
            pastMandatory = emptyList(),
        ).formatDates()
    }

    override suspend fun getAssignment(classmeetingsId: List<Int>): List<Assignment> {
        val studentId = appSettings.getStudentId()
        return diarySource.getAssignments(studentId, classmeetingsId)

    }

    override suspend fun getAttachments(assignmentId: Int): List<AttachmentEntity> {
        return diarySource.getAttachment(assignmentId)
    }
}
