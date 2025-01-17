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
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.attachments.AttachmentsSource
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.SendFileRequestEntity
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

const val ANNOUNCEMENT = "announcement"
const val HOMEWORK = "homework"
const val ANSWERS = "answers"

@Module
@InstallIn(SingletonComponent::class)
class AttachmentDownloadManager
    @Inject
    constructor(
        private val attachmentsSource: AttachmentsSource,
    ) : AttachmentDownloadManagerInterface {
        private val _permission_access = MutableStateFlow<Boolean?>(null)
        val permission_access: StateFlow<Boolean?> = _permission_access

        override suspend fun downloadFile(
            context: Context,
            fileUiEntity: FileUiEntity,
        ): String? {
            val file =
                getFile(context, fileUiEntity.assignType, fileUiEntity.assignId, fileUiEntity.fileName)

            withContext(Dispatchers.IO) {
                file.createNewFile()
            }

            return attachmentsSource.downloadAttachment(fileUiEntity.id!!, file)
        }

        override suspend fun uploadFiles(
            context: Context,
            files: List<FileUiEntity>,
        ) {
            files.forEach {
                if (it.id == null) {
                    val file = getFile(context, it.assignType, it.assignId, it.fileName)
                    val inputStream = context.contentResolver.openInputStream(Uri.fromFile(file))
                    val body = inputStream?.readBytes()?.toRequestBody("*/*".toMediaTypeOrNull())
                    if (body != null) {
                        val part = MultipartBody.Part.createFormData("file", it.fileName, body)
                        val data =
                            SendFileRequestEntity(
                                true,
                                it.assignId,
                                it.description,
                                it.fileName,
                            )
                        withContext(Dispatchers.IO) {
                            attachmentsSource.sendFileAttachment(part, data)
                            inputStream.close()
                        }
                    }
                }
            }
        }

        override fun editDescription(
            attachmentId: Int,
            description: String?,
        ) {
            TODO("Not yet implemented")
        }

        override fun openFile(
            context: Context,
            fileUiEntity: FileUiEntity,
        ) {
            val file =
                getFile(
                    context,
                    fileUiEntity.assignType,
                    fileUiEntity.assignId,
                    fileUiEntity.fileName,
                )
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file,
                    ),
                )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        }

        override fun getFile(
            context: Context,
            assignType: String,
            assignId: Int,
            attachmentName: String,
        ): File {
            val downloads = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            downloads?.mkdirs()
            val typesFolder = File(downloads, assignType)
            typesFolder.mkdirs()
            val idFolder = File(typesFolder, assignId.toString())
            idFolder.mkdirs()
            return File(typesFolder, attachmentName)
        }

        override suspend fun doAfterGetPermission(
            context: Context,
            block: suspend () -> Unit,
        ) {
            if (Build.VERSION.SDK_INT <= 32) {
                when (_permission_access.value) {
                    null -> {
                        _permission_access.value = AttachmentsUtils.checkPermissions(context)
                        doAfterGetPermission(context, block)
                    }

                    true -> {
                        block.invoke()
                    }

                    else -> {
                        permission_access.first {
                            if (it == true) block.invoke()
                            it == true
                        }
                    }
                }
            } else {
                block.invoke()
            }
        }

        override suspend fun changePermissionStatus(status: Boolean?) {
            _permission_access.value = status
        }
    }
