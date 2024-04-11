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
