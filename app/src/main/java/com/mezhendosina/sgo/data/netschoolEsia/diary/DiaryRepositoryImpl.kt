package com.mezhendosina.sgo.data.netschoolEsia.diary

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import com.mezhendosina.sgo.app.model.journal.entities.DiaryUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.LessonUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.WeekDayUiEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl
@Inject
constructor(
    val appSettings: AppSettings,
    val diarySource: DiarySource,
    @ApplicationContext val context: Context
) : DiaryRepository {
    override suspend fun getDiary(
        startDate: String,
        endDate: String,
    ): DiaryUiEntity {
        val studentId = appSettings.getStudentId()
        val getDiary = diarySource.getDiary(studentId, startDate, endDate)
        val weekDays = mutableListOf<WeekDayUiEntity>(
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(startDate, mutableListOf()),
            WeekDayUiEntity(endDate, mutableListOf()),
        )

        getDiary.forEach { classmeeting ->
            val weekDay = weekDays.indexOfFirst { it.date == classmeeting.day }
            if (weekDay != -1) {
                weekDays[weekDay].lessons.add(
                    LessonUiEntity(
                        emptyList(),
                        null,
                        classmeeting.classmeetingId,
                        classmeeting.day,
                        classmeeting.endTime,
                        false,
                        classmeeting.order,
                        classmeeting.scheduleTimeRelay,
                        classmeeting.startTime,
                        classmeeting.subjectName
                    )
                )
            }
        }
        withContext(Dispatchers.Main){
            Toast.makeText(context, getDiary.toString(), Toast.LENGTH_SHORT).show()
        }

        return DiaryUiEntity(
            weekDays = weekDays,
            weekStart = startDate,
            weekEnd = endDate,
            pastMandatory = emptyList(),
        )
    }

    override suspend fun getAssignment(classmeetingId: Int): List<Assignment> {
        val studentId = appSettings.getStudentId()
        return diarySource.getAssignments(studentId, classmeetingId)
    }

    override suspend fun getAttachments(assignmentId: Int): List<AttachmentEntity> {
        return diarySource.getAttachment(assignmentId)
    }
}
