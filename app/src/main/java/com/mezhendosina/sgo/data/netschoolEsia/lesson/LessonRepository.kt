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

package com.mezhendosina.sgo.data.netschoolEsia.lesson

import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.LessonUiEntity
import com.mezhendosina.sgo.app.uiEntities.AboutLessonUiEntity
import kotlinx.coroutines.flow.StateFlow

interface LessonRepository {
    val lesson: StateFlow<AboutLessonUiEntity?>

    fun getAnswerText(): String

    suspend fun editAnswerText(text: String)

    suspend fun getAboutLesson(
        lessonUiEntity: LessonUiEntity,
        studentId: Int,
    )

    fun editAnswers(files: List<FileUiEntity>?)
}
