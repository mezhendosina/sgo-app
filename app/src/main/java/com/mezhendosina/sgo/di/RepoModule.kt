package com.mezhendosina.sgo.di

import com.mezhendosina.sgo.app.model.attachments.AttachmentDownloadManager
import com.mezhendosina.sgo.app.model.attachments.AttachmentDownloadManagerInterface
import com.mezhendosina.sgo.data.netschool.repo.LessonRepository
import com.mezhendosina.sgo.data.netschool.repo.LessonRepositoryInterface
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepository
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepositoryImpl
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepository
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepositoryImpl
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
    abstract fun bindLessonRepository(lessonRepository: LessonRepository): LessonRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindGradesRepository(gradesRepository: GradesRepository): GradesRepositoryImpl

    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepository: LoginRepository): LoginRepositoryImpl
}
