package com.mezhendosina.sgo.data.requests.sgo.diary.entities

data class PastMandatoryEntity(
    val id: Int,
    val subjectName: String,
    val assignmentName: String,
    val dueDate: String
)