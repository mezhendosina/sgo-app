package com.mezhendosina.sgo.data.requests.sgo.base

import com.mezhendosina.sgo.app.SourcesProvider
import com.mezhendosina.sgo.app.model.announcements.AnnouncementsSource
import com.mezhendosina.sgo.app.model.chooseSchool.SchoolsSource
import com.mezhendosina.sgo.app.model.grades.GradesSource
import com.mezhendosina.sgo.app.model.homework.HomeworkSource
import com.mezhendosina.sgo.app.model.journal.DiarySource
import com.mezhendosina.sgo.app.model.login.LoginSource
import com.mezhendosina.sgo.app.model.settings.SettingsSource
import com.mezhendosina.sgo.data.requests.sgo.announcements.RetrofitAnnouncementsSource
import com.mezhendosina.sgo.data.requests.sgo.diary.RetrofitDiarySource
import com.mezhendosina.sgo.data.requests.sgo.grades.RetrofitGradesService
import com.mezhendosina.sgo.data.requests.sgo.homework.RetrofitHomeworkSource
import com.mezhendosina.sgo.data.requests.sgo.login.RetrofitLoginSource
import com.mezhendosina.sgo.data.requests.sgo.school.RetrofitSchoolsSource
import com.mezhendosina.sgo.data.requests.sgo.settings.RetrofitSettingsSource

class RetrofitSourcesProvider(private val config: RetrofitConfig) : SourcesProvider {
    override fun getSchoolsSource(): SchoolsSource {
        return RetrofitSchoolsSource(config)
    }

    override fun getLoginSource(): LoginSource {
        return RetrofitLoginSource(config)
    }

    override fun getDiarySource(): DiarySource {
        return RetrofitDiarySource(config)
    }

    override fun getHomeworkSource(): HomeworkSource {
        return RetrofitHomeworkSource(config)
    }

    override fun getAnnouncementsSource(): AnnouncementsSource {
        return RetrofitAnnouncementsSource(
            config
        )
    }

    override fun getSettingsSource(): SettingsSource {
        return RetrofitSettingsSource(config)
    }

    override fun getGradesSource(): GradesSource {
        return RetrofitGradesService(config)
    }
}