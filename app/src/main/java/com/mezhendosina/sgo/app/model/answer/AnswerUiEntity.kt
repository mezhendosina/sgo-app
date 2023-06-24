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

package com.mezhendosina.sgo.app.model.answer

import android.net.Uri

data class AnswerUiEntity(
    val text: String?,
    val files: List<FileUiEntity>
)

data class FileUiEntity(
    val id: Int?,
    val fileName: String,
    val description: String?,
    val file: Uri? = null
) {
    fun addId(id: Int?): FileUiEntity = FileUiEntity(id, fileName, description, file)
}