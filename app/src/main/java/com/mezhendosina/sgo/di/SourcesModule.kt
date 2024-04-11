package com.mezhendosina.sgo.di

import com.mezhendosina.sgo.app.model.announcements.AnnouncementsSource
import com.mezhendosina.sgo.data.netschoolEsia.announcements.RetrofitAnnouncementsSource
import com.mezhendosina.sgo.data.netschoolEsia.attachments.AttachmentsSource
import com.mezhendosina.sgo.data.netschoolEsia.attachments.RetrofitAttachmentsSource
import com.mezhendosina.sgo.data.netschoolEsia.diary.DiarySource
import com.mezhendosina.sgo.data.netschoolEsia.diary.RetrofitDiarySource
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesSource
import com.mezhendosina.sgo.data.netschoolEsia.grades.RetrofitGradesSource
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginSource
import com.mezhendosina.sgo.data.netschoolEsia.login.RetrofitLoginSource
import com.mezhendosina.sgo.data.netschoolEsia.utils.RetrofitUtilsSource
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {
    @Binds
    abstract fun bindAnnouncementsSource(retrofitAnnouncementsSource: RetrofitAnnouncementsSource): AnnouncementsSource

    @Binds
    abstract fun bindAttachmentsSource(retrofitAttachmentsSource: RetrofitAttachmentsSource): AttachmentsSource

    @Binds
    abstract fun bindDiarySource(retrofitDiarySource: RetrofitDiarySource): DiarySource

    @Binds
    abstract fun bindGradesSource(retrofitGradesSource: RetrofitGradesSource): GradesSource

    @Binds
    abstract fun bindLoginSource(retrofitLoginSource: RetrofitLoginSource): LoginSource

    @Binds
    abstract fun bindUtilsSource(retrofitUtilsSource: RetrofitUtilsSource): UtilsSource
}
