package com.mezhendosina.sgo.data.room.entities

import androidx.room.*
import com.mezhendosina.sgo.app.model.journal.entities.AssignmentUiEntity
import com.mezhendosina.sgo.data.requests.diary.entities.TextAnswer

data class AssignmentsWithMarkAndAttachments(
    @Embedded val assignmentsRoomEntity: AssignmentsRoomEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "assignmentId"
    ) val mark: MarksRoomEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "assignmentId"
    ) val attachments: List<AttachmentsRoomEntity>?
) {
    fun toAssignmentEntity(): AssignmentUiEntity = AssignmentUiEntity(
        assignmentName = assignmentsRoomEntity.name,
        classMeetingId = assignmentsRoomEntity.classMeetingId,
        dueDate = assignmentsRoomEntity.dueDate,
        id = assignmentsRoomEntity.id,
        mark = mark?.toMarkEntity(),
        textAnswer = assignmentsRoomEntity.textAnswer?.let {
            assignmentsRoomEntity.textAnswerDate?.let { it1 ->
                TextAnswer(
                    it,
                    it1
                )
            }
        },
        typeId = assignmentsRoomEntity.typeId,
        weight = assignmentsRoomEntity.weight,
        attachments = attachments?.map { it.toUiEntity() } ?: emptyList()
    )
}

@Entity(tableName = "assignments_database")
data class AssignmentsRoomEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val typeId: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val weight: Int,
    @ColumnInfo val dueDate: String,
    @ColumnInfo val classMeetingId: Int,
    @ColumnInfo val textAnswer: String?,
    @ColumnInfo val textAnswerDate: String?,
)
