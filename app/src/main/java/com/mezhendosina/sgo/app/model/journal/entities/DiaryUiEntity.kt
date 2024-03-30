/*
 * Copyright 2023 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mezhendosina.sgo.app.model.journal.entities

import com.mezhendosina.sgo.app.uiEntities.MarkUiEntity
import com.mezhendosina.sgo.data.dateToRussian
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.AttachmentsResponseEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.diary.PastMandatoryEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.diary.TextAnswer

data class DiaryUiEntity(
    val weekDays: List<WeekDayUiEntity>,
    val pastMandatory: List<PastMandatoryEntity>,
) {
    fun getClassmeetingsId(): List<Int> {
        val out = mutableListOf<Int>()
        weekDays.forEach {
            it.lessons.forEach { lesson ->
                out.add(
                    lesson.classmeetingId
                )
            }
        }
        return out
    }

    fun formatDates(): DiaryUiEntity {
        return DiaryUiEntity(
            weekDays.map {
                it.formatDate()
            },
            pastMandatory
        )
    }

    fun addAssignments(assignments: List<AssignmentUiEntity>): DiaryUiEntity {
        val newWeekDays = weekDays.map { weekDay ->
            val weekDaysWithAssignments = weekDay.lessons.map { lesson ->
                val lessonAssignments = assignments.filter {
                    it.classMeetingId == lesson.classmeetingId
                }
                val lessonWithAssignments = lesson.addAssignments(lessonAssignments)
                val lessonWithHomework =
                    lessonAssignments.firstOrNull { it.typeId == 3 }?.let { lessonWithAssignments.addHomework(it) }
                return@map lessonWithHomework ?: lessonWithAssignments
            }
            return@map WeekDayUiEntity(
                weekDay.date,
                weekDaysWithAssignments.toMutableList()
            )
        }

        return DiaryUiEntity(
            newWeekDays, pastMandatory
        )
    }
}

data class WeekDayUiEntity(
    val date: String,
    val lessons: MutableList<LessonUiEntity>,
) {
    fun formatDate(): WeekDayUiEntity = WeekDayUiEntity(
        dateToRussian(date), lessons
    )
}

data class LessonUiEntity(
    val assignments: List<AssignmentUiEntity>?,
    val homework: AssignmentUiEntity?,
    val classmeetingId: Int,
    val day: String,
    val endTime: String,
    val isEaLesson: Boolean,
    val number: Int,
    val relay: Int,
    val startTime: String,
    val subjectName: String,
) {
    fun addAssignments(assignments: List<AssignmentUiEntity>): LessonUiEntity =
        LessonUiEntity(
            assignments,
            homework,
            classmeetingId,
            day,
            endTime,
            isEaLesson,
            number,
            relay,
            startTime,
            subjectName,
        )

    fun addHomework(homework: AssignmentUiEntity) =
        LessonUiEntity(
            assignments,
            homework,
            classmeetingId,
            day,
            endTime,
            isEaLesson,
            number,
            relay,
            startTime,
            subjectName,
        )

    fun addWhyGrades(marks: List<MarkUiEntity>): LessonUiEntity {
        val outAssignments =
            mutableListOf<AssignmentUiEntity>()
        assignments?.forEach { assign ->
            val mark = marks.firstOrNull { it.id == assign.id }
            if (mark != null) {
                outAssignments.add(
                    AssignmentUiEntity(
                        assign.assignmentName,
                        assign.classMeetingId,
                        assign.dueDate,
                        assign.id,
                        mark,
                        assign.textAnswer,
                        assign.typeId,
                        assign.weight,
                        assign.attachments,
                    ),
                )
            } else {
                outAssignments.add(assign)
            }
        }
        return LessonUiEntity(
            outAssignments,
            homework,
            classmeetingId,
            day,
            endTime,
            isEaLesson,
            number,
            relay,
            startTime,
            subjectName,
        )
    }
}

data class AssignmentUiEntity(
    val assignmentName: String,
    val classMeetingId: Int,
    val dueDate: String,
    val id: Int,
    val mark: MarkUiEntity?,
    val textAnswer: TextAnswer?,
    val typeId: Int,
    val weight: Int,
    val attachments: List<AttachmentsResponseEntity>,
)

//
// data class MarkUiEntity(
//    val assignId: Int,
//    val assignName: String,
//    val comment: String?,
//    val mark: Int,
//    val dutyMark: Boolean,
//    val resultScore: Int,
//    val typeId: Int,
// )
