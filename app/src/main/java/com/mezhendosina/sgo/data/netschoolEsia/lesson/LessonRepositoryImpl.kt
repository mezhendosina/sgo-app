package com.mezhendosina.sgo.data.netschoolEsia.lesson

import com.mezhendosina.sgo.app.model.answer.AnswerRepository
import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.LessonUiEntity
import com.mezhendosina.sgo.app.uiEntities.AboutLessonUiEntity
import com.mezhendosina.sgo.app.uiEntities.MarkUiEntity
import com.mezhendosina.sgo.app.uiEntities.WhyGradeEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.netschoolEsia.diary.DiaryRepository
import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonRepositoryImpl
@Inject
constructor(
    val diaryRepository: DiaryRepository,
    private val answerRepository: AnswerRepository,
    val appSettings: AppSettings,
) : LessonRepository {
    private val _lesson = MutableStateFlow<AboutLessonUiEntity?>(null)
    override val lesson: StateFlow<AboutLessonUiEntity?> = _lesson

    private var answerText = ""

    override fun getAnswerText(): String {
        return "TODO"
    }

    override suspend fun editAnswerText(text: String) {
        val studentId = appSettings.getStudentId()
        answerRepository.sendTextAnswer(
            _lesson.value?.homeworkId ?: return,
            text,
            studentId
        )
        withContext(Dispatchers.Main) {
            _lesson.value = lesson.value?.editAnswers(text, lesson.value?.answerFiles)
        }
    }

    override suspend fun getAboutLesson(
        lessonUiEntity: LessonUiEntity,
        studentId: Int,
    ) {
        val assignmentResp = diaryRepository.getAssignment(listOf(lessonUiEntity.classmeetingId))
        val homework = assignmentResp.first { it.assignmentTypeId == 3 }

        val attachments = mutableListOf<AttachmentEntity>()
        val whyGradeEntity = mutableListOf<WhyGradeEntity>()

        assignmentResp.forEach {
            withContext(Dispatchers.IO) {
                if (it.attachmentsExists) {
                    val getAttachments = diaryRepository.getAttachments(it.assignmentId)
                    synchronized(attachments) {
                        attachments.addAll(getAttachments)
                    }
                }
            }
            if (it.result != null || it.duty) {
                whyGradeEntity.add(
                    WhyGradeEntity(
                        it.assignmentName,
                        MarkUiEntity(
                            it.assignmentId,
                            it.result!!.toInt(),
                            it.comment,
                            it.duty,
                            null,
                        ),
                        it.assignmentTypeName,
                    ),
                )
            }
        }

        _lesson.value =
            AboutLessonUiEntity(
                lessonUiEntity.classmeetingId,
                lessonUiEntity.subjectName,
                lessonUiEntity.homework!!.id,
                lessonUiEntity.homework.assignmentName,
                homework.comment,
                attachments.map {
                    FileUiEntity(
                        it.attachmentId,
                        it.attachmentId.toString(),
                        it.attachmentId,
                        it.fileName,
                        it.description?.toString()
                    )
                },
                whyGradeEntity,
                null,
                emptyList(),
            )
    }

    override fun editAnswers(files: List<FileUiEntity>?) {
        TODO("Not yet implemented")
    }
}
