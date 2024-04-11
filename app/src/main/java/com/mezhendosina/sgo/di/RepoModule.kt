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

package com.mezhendosina.sgo.di

import com.mezhendosina.sgo.app.model.attachments.AttachmentDownloadManager
import com.mezhendosina.sgo.app.model.attachments.AttachmentDownloadManagerInterface
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.calendar.CalendarRepositoryImpl
import com.mezhendosina.sgo.data.netschoolEsia.diary.DiaryRepository
import com.mezhendosina.sgo.data.netschoolEsia.diary.DiaryRepositoryImpl
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepository
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepositoryImpl
import com.mezhendosina.sgo.data.netschoolEsia.lesson.LessonRepository
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepository
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepositoryImpl
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindAttachmentDownloadManger(attachmentDownloadManager: AttachmentDownloadManager): AttachmentDownloadManagerInterface

    @Binds
    @Singleton
    abstract fun bindLessonRepository(lessonRepository: com.mezhendosina.sgo.data.netschoolEsia.lesson.LessonRepositoryImpl): LessonRepository

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(diaryRepositoryImpl: DiaryRepositoryImpl): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindGradesRepository(gradesRepository: GradesRepositoryImpl): GradesRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepository: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindUtilsRepo(utilsRepositoryImpl: UtilsRepositoryImpl): UtilsRepository

    @Binds
    @Singleton
    abstract fun bindCalendarRepo(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository
}
