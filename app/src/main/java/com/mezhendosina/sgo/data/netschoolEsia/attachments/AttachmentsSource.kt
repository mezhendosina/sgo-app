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

package com.mezhendosina.sgo.data.netschoolEsia.attachments

import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.AttachmentsRequestEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.AttachmentsResponseEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.SendFileRequestEntity
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

interface AttachmentsSource {
    suspend fun getAttachments(
        studentId: Int,
        attachmentsRequestEntity: AttachmentsRequestEntity,
    ): List<AttachmentsResponseEntity>

    suspend fun downloadAttachment(
        attachmentId: Int,
        file: File,
    ): String?

    suspend fun deleteAttachment(
        assignmentId: Int,
        attachmentId: Int,
    )

    suspend fun editAttachmentDescription(
        attachmentId: Int,
        description: String,
    ): String

    suspend fun sendTextAnswer(
        assignmentId: Int,
        studentId: Int,
        answer: String,
    ): Response<ResponseBody>

    suspend fun sendFileAttachment(
        file: MultipartBody.Part,
        data: SendFileRequestEntity,
    ): Int
}
