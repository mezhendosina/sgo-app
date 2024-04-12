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

package com.mezhendosina.sgo.app.model.attachments

import android.content.Context
import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import java.io.File

interface AttachmentDownloadManagerInterface {

    suspend fun downloadFile(
        context: Context,
        fileUiEntity: FileUiEntity
    ): String?

    suspend fun uploadFiles(
        context: Context,
        files: List<FileUiEntity>
    )

    fun editDescription(attachmentId: Int, description: String?)

    fun openFile(context: Context, fileUiEntity: FileUiEntity)

    fun getFile(context: Context, assignType: String, assignId: Int, attachmentName: String): File

    suspend fun doAfterGetPermission(context: Context, block: suspend () -> Unit)

    suspend fun changePermissionStatus(status: Boolean?)
}
