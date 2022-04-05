package com.che.zadali.sgoapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.che.zadali.sgo_app.data.diary.Lesson
import com.che.zadali.sgoapp.data.layout.announcements.AnnouncementsDataItem
import com.che.zadali.sgoapp.data.services.AnnouncementsActionListener
import com.che.zadali.sgoapp.data.services.AnnouncementsService
import com.che.zadali.sgoapp.data.services.DiaryService
import com.che.zadali.sgoapp.data.services.TodayActionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainScreenViewModel(private val DiaryService: DiaryService, private val AnnouncementsService: AnnouncementsService) : ViewModel() {
    private var _lessons = MutableLiveData<List<Lesson>>()
    var lessons: LiveData<List<Lesson>> = _lessons

    private var _announcements = MutableLiveData<List<AnnouncementsDataItem>>()
    var announcements: LiveData<List<AnnouncementsDataItem>> = _announcements

    private var _grades = MutableLiveData<List<Any>>()//TODO изменить тип массива
    var grades: LiveData<List<Any>> = _grades //TODO изменить тип массива

    //TODO gradesListener

    private val announcementsListener: AnnouncementsActionListener = {
        _announcements.value = it
    }

    private val todayListener: TodayActionListener = {
        _lessons.value = it
    }


    init {
        loadTodayLessons()
        loadAnnouncements()
//        loadGrades()
    }

    private fun loadTodayLessons() {
        DiaryService.addTodayListener(todayListener)
        DiaryService.loadToday()
    }

    private fun loadAnnouncements(){
        AnnouncementsService.addListener(announcementsListener)
        AnnouncementsService.loadAnnouncements()
    }

    private fun loadGrades(){
        TODO("Сделать загрузку оценок")
    }

    override fun onCleared() {
        super.onCleared()
        DiaryService.removeTodayListener(todayListener)
        AnnouncementsService.removeListener(announcementsListener)
    }
}