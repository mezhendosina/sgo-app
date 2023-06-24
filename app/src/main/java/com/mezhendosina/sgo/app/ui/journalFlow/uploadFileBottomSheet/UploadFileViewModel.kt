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

package com.mezhendosina.sgo.app.ui.journalFlow.uploadFileBottomSheet

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

//class UploadFileViewModel(
//    private val homeworkSource: HomeworkSource = NetSchoolSingleton.homeworkSource
//) : ViewModel() {
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    private val _isLoading = MutableLiveData(false)
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _success = MutableLiveData<Boolean>()
//    val success: LiveData<Boolean> = _success
//
//    fun sendFile(
//        context: Context,
//        assignmentID: Int,
//        filePath: Uri,
//        description: String
//    ) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                withContext(Dispatchers.Main) {
//                    _isLoading.value = true
//                    _success.value = false
//                }
//                val contentResolver = context.contentResolver
//                val a = contentResolver.openInputStream(filePath)
//
//                val body = a?.readBytes()?.toRequestBody("*/*".toMediaTypeOrNull())
//                val fileName = getFileNameFromUri(context, filePath)
//                if (body != null) {
//                    val part = MultipartBody.Part.createFormData("file", fileName, body)
//
//                    homeworkSource.sendFileAttachment(
//                        part,
//                        SendFileRequestEntity(
//                            true,
//                            assignmentID,
//                            description,
//                            fileName ?: ""
//                        )
//                    )
//                }
//                withContext(Dispatchers.IO) { a?.close() }
//                withContext(Dispatchers.Main) {
//                    _success.value = true
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    _errorMessage.value = e.toDescription()
//                }
//            } finally {
//                withContext(Dispatchers.Main) {
//                    _isLoading.value = false
//                }
//            }
//        }
//    }
//
//    fun editFileDescription(fileId: Int, description: String) {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                _success.value = false
//                withContext(Dispatchers.IO) {
//                    attachmentsRepository.editDescription(fileId, description)
//                }
//                _success.value = true
//            } catch (e: Exception) {
//                _errorMessage.value = e.toDescription()
//            }
//        }
//    }
//
//}

@SuppressLint("Range")
fun getFileNameFromUri(context: Context, uri: Uri?): String? {
    return if (uri != null) {
        val fileName: String?
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        fileName = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        cursor?.close()
        fileName
    } else {
        ""
    }
}


//    String scheme = uri.getScheme();
//    if (scheme.equals("file")) {
//        fileName = uri.getLastPathSegment();
//    }
//    else if (scheme.equals("content")) {
//        String[] proj = { MediaStore.Images.Media.TITLE };
//        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor != null && cursor.getCount() != 0) {
//            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
//            cursor.moveToFirst();
//            fileName = cursor.getString(columnIndex);
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
//    }
