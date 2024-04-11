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

import com.mezhendosina.sgo.data.netschoolEsia.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschoolEsia.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.AttachmentsRequestEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.AttachmentsResponseEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.DeleteAttachmentRequestEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.SendFileRequestEntity
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAttachmentsSource
    @Inject
    constructor(config: RetrofitConfig) :
    BaseRetrofitSource(config), AttachmentsSource {
        private val attachmentsSource = config.baseRetrofit.create(AttachmentsApi::class.java)

        override suspend fun getAttachments(
            studentId: Int,
            attachmentsRequestEntity: AttachmentsRequestEntity,
        ): List<AttachmentsResponseEntity> =
            wrapRetrofitExceptions {
                attachmentsSource.getAttachments(
                    studentId,
                    attachmentsRequestEntity,
                )
            }

        override suspend fun downloadAttachment(
            attachmentId: Int,
            file: File,
        ): String? =
            wrapRetrofitExceptions {
                val request = attachmentsSource.downloadAttachment(attachmentId)
                request.body()?.byteStream()?.readBytes()
                    ?.let { file.writeBytes(it) }
                return@wrapRetrofitExceptions request.headers()["Content-Type"]
            }

        override suspend fun deleteAttachment(
            assignmentId: Int,
            attachmentId: Int,
        ): Unit =
            wrapRetrofitExceptions {
                attachmentsSource.deleteAttachment(
                    attachmentId,
                    DeleteAttachmentRequestEntity(assignmentId),
                )
            }

        override suspend fun editAttachmentDescription(
            attachmentId: Int,
            description: String,
        ): String =
            wrapRetrofitExceptions {
                attachmentsSource.editAttachmentDescription(attachmentId, description)
            }

        override suspend fun sendTextAnswer(
            assignmentId: Int,
            studentId: Int,
            answer: String,
        ): Response<ResponseBody> =
            wrapRetrofitExceptions {
                val r = attachmentsSource.sendTextAnswer(assignmentId, studentId, answer)
                r
            }

        override suspend fun sendFileAttachment(
            file: MultipartBody.Part,
            data: SendFileRequestEntity,
        ) = wrapRetrofitExceptions {
            attachmentsSource.sendFileAttachment(
                file,
                data,
            )
        }
    }
