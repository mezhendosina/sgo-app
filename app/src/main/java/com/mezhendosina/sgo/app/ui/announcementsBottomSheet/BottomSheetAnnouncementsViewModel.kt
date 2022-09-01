package com.mezhendosina.sgo.app.ui.announcementsBottomSheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.model.announcements.AnnouncementsActionListener
import com.mezhendosina.sgo.app.model.announcements.AnnouncementsRepository
import com.mezhendosina.sgo.app.toDescription
import com.mezhendosina.sgo.data.requests.announcements.AnnouncementsResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetAnnouncementsViewModel(
    private val announcementsRepository: AnnouncementsRepository = Singleton.announcementsRepository
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _announcements = MutableLiveData<List<AnnouncementsResponseEntity>>()
    val announcements: LiveData<List<AnnouncementsResponseEntity>> = _announcements

    private val announcementsListener: AnnouncementsActionListener = {
        _announcements.value = it
    }

    init {
        announcementsRepository.addListener(announcementsListener)

    }
    fun loadAnnouncements() {
        if (Singleton.announcements.isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    announcementsRepository.announcements()
                } catch (e: Exception) {
                    val message = e.toDescription()
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = message
                    }
                }
            }
        } else {
            _announcements.value = Singleton.announcements
        }
    }

    override fun onCleared() {
        super.onCleared()
        announcementsRepository.removeListener(announcementsListener)

    }
}