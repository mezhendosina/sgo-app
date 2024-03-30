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