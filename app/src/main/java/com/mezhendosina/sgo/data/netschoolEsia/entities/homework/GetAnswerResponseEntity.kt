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

package com.mezhendosina.sgo.data.netschoolEsia.entities.homework

import com.mezhendosina.sgo.app.model.answer.AnswerUiEntity
import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.Attachment
import com.mezhendosina.sgo.data.netschoolEsia.entities.diary.TextAnswer

data class GetAnswerResponseEntity(
    val files: List<FileEntity>,
    val studentId: Int,
    val text: TextAnswer?,
) {
    fun toUiEntity(
        assignType: String,
        assignId: Int,
    ) = AnswerUiEntity(
        text?.answer,
        files.map { it.toUiEntity(assignType, assignId) },
    )
}

data class FileEntity(
    val aFile: Any,
    val attachmentDate: String,
    val description: String?,
    val fileName: String,
    val id: Int,
    val saved: Int,
    val userId: Int?,
) {
    fun toUiEntity(
        assignType: String,
        assignId: Int,
    ) = FileUiEntity(id, assignType, assignId, fileName, description)

    fun toAttachmentEntity() = Attachment(description, id, fileName, fileName)
}
