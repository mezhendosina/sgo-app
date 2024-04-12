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

package com.mezhendosina.sgo.data.netschoolEsia.entities.assignments

import com.mezhendosina.sgo.app.model.journal.entities.AssignmentUiEntity
import com.mezhendosina.sgo.app.uiEntities.MarkUiEntity

data class Assignment(
    val classmeetingId: Int,
    val assignmentId: Int,
    val assignmentName: String,
    val description: String,
    val result: Float?,
    val classAssignment: Boolean,
    val duty: Boolean,
    val comment: String?,
    val assignmentTypeId: Int,
    val assignmentTypeAbbr: String,
    val assignmentTypeName: String,
    val weight: Int,
    val attachmentsExists: Boolean,
    val hasTextAnswer: Boolean,
    val hasFileAnswers: Boolean,
    val subjectId: Int,
    val subjectName: String,
    val dueDate: String,
    val answerFilesCount: Int,
    val extraActivity: Boolean,
    val resultDate: String,
    val assignmentDate: String,
    val canAnswer: Boolean,
) {
    fun toUiEntity(): AssignmentUiEntity = AssignmentUiEntity(
        assignmentName,
        classmeetingId,
        dueDate,
        assignmentId,
        if (result != null) MarkUiEntity(
            assignmentId,
            result.toInt(),
            comment,
            duty,
            null
        ) else null,
        null,
        assignmentTypeId,
        weight,
        emptyList()
    )
}