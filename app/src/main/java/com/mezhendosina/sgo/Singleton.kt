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

package com.mezhendosina.sgo

import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import com.mezhendosina.sgo.app.model.journal.DiaryStyle
import com.mezhendosina.sgo.app.model.journal.entities.DiaryUiEntity
import com.mezhendosina.sgo.app.model.journal.entities.LessonUiEntity
import com.mezhendosina.sgo.app.ui.main.container.ContainerFragment
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.app.uiEntities.UserUIEntity
import com.mezhendosina.sgo.app.utils.LoadStates
import com.mezhendosina.sgo.data.WeekStartEndEntity
import com.mezhendosina.sgo.data.netschoolEsia.announcements.AnnouncementsResponseEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.diary.PastMandatoryEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem

object Singleton {
    var loggedIn = false
    var at = ""
    var welcomeShowed = false
    var announcements: List<AnnouncementsResponseEntity> = emptyList()
    var selectedAnnouncement: AnnouncementsResponseEntity? = null

    var users: List<UserUIEntity> = emptyList()
    var lesson: LessonUiEntity? = null
    var pastMandatoryItem: PastMandatoryEntity? = null

    var grades: List<GradesItem> = emptyList()
    val gradesRecyclerViewLoaded = MutableLiveData<Boolean>(true)

    val weeks = mutableListOf<WeekStartEndEntity>()

    var currentWeek: Int? = null
    val currentDiaryUiEntity = MutableLiveData<DiaryUiEntity>()

    var journalTabsLayout: TabLayout? = null

    val answerUpdated = MutableLiveData<Boolean>(false)

    var gradesWithWeight = false


    val mainContainerScreen = MutableLiveData<String>(ContainerFragment.JOURNAL)
}
