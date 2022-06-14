package com.mezhendosina.sgo.app.ui.journal

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.ui.errorDialog
import com.mezhendosina.sgo.data.ErrorResponse
import com.mezhendosina.sgo.data.Settings
import com.mezhendosina.sgo.data.layouts.attachments.AttachmentsResponseItem
import com.mezhendosina.sgo.data.layouts.diary.diary.Mark
import com.mezhendosina.sgo.data.layouts.diary.diary.WeekDay
import com.mezhendosina.sgo.data.layouts.pastMandatory.PastMandatoryItem
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class JournalViewModel(private val journalService: JournalService) : ViewModel() {

    private val _diary = MutableLiveData<List<WeekDay>>()
    val diary: LiveData<List<WeekDay>> = _diary

    private val _attachments = MutableLiveData<List<AttachmentsResponseItem>>()
    val attachments: LiveData<List<AttachmentsResponseItem>> = _attachments

    private val _grades = MutableLiveData<List<Mark>>()
    val grades: LiveData<List<Mark>> = _grades

    private val diaryListener: journalActionListener = {
        _diary.value = it
    }
    private val attachmentsListener: attachmentActionListener = {
        _attachments.value = it
    }
    private val pastMandatoryListener: pastMandatoryActionListener = {
        _pastMandatory.value = it
    }

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _pastMandatory = MutableLiveData<List<PastMandatoryItem>>()
    val pastMandatory: LiveData<List<PastMandatoryItem>> = _pastMandatory


    fun getDiary(context: Context) {
        journalService.addListener(diaryListener, attachmentsListener, pastMandatoryListener)
        CoroutineScope(Dispatchers.IO).launch {
            val settings = Settings(context)
            try {
                journalService.loadDiary(
                    settings.currentUserId.first(),
                    Singleton.currentYearId,
                    currentWeekStart(),
                    currentWeekEnd(),
                    _isEmpty
                )
            } catch (e: ResponseException) {
                withContext(Dispatchers.Main) {
                    errorDialog(context, e.response.body<ErrorResponse>().message)
                }
            }
        }
    }


    fun refreshDiary(context: Context, swipeRefreshLayout: SwipeRefreshLayout) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                swipeRefreshLayout.isRefreshing = true
            }
            val singleton = Singleton
            val settings = Settings(context)
            try {
                journalService.reloadDiary(
                    settings.currentUserId.first(),
                    Singleton.currentYearId,
                    weekStart(singleton.currentWeek),
                    weekEnd(singleton.currentWeek),
                    _isEmpty
                )
            } catch (e: ResponseException) {
                withContext(Dispatchers.Main) {
                    errorDialog(context, e.response.body<ErrorResponse>().message)
                }
            } finally {
                withContext(Dispatchers.Main) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    fun nextWeek(context: Context, swipeRefreshLayout: SwipeRefreshLayout) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                swipeRefreshLayout.isRefreshing = true
            }
            val singleton = Singleton
            val settings = Settings(context)
            singleton.currentWeek += 1
            journalService.reloadDiary(
                settings.currentUserId.first(),
                Singleton.currentYearId,
                weekStart(singleton.currentWeek),
                weekEnd(singleton.currentWeek),
                _isEmpty
            )
            withContext(Dispatchers.Main) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    fun previousWeek(context: Context, swipeRefreshLayout: SwipeRefreshLayout) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                swipeRefreshLayout.isRefreshing = true
            }
            val singleton = Singleton
            val settings = Settings(context)
            singleton.currentWeek -= 1
            println(
                "${weekStart(singleton.currentWeek)} ${weekEnd(singleton.currentWeek)}"
            )
            journalService.reloadDiary(
                settings.currentUserId.first(),
                singleton.currentYearId,
                weekStart(singleton.currentWeek),
                weekEnd(singleton.currentWeek),
                _isEmpty
            )
            withContext(Dispatchers.Main) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        journalService.removeListener(diaryListener, attachmentsListener, pastMandatoryListener)
    }

    @SuppressLint("SimpleDateFormat")
    private fun currentWeekStart(): String {
        val s = SimpleDateFormat("w.yyyy").format(Date().time)
        val a = SimpleDateFormat("w.yyyy").parse(s)
        return SimpleDateFormat("yyyy-MM-dd").format(a!!)
    }

    @SuppressLint("SimpleDateFormat")
    private fun currentWeekEnd(): String {
        val s = SimpleDateFormat("w.yyyy").format(Date().time)
        val a = SimpleDateFormat("w.yyyy").parse(s)!!.time + 6 * 24 * 60 * 60 * 1000
        return SimpleDateFormat("yyyy-MM-dd").format(a)
    }

}

@SuppressLint("SimpleDateFormat")
fun weekStart(week: Int): String {
    val s = SimpleDateFormat("w.yyyy").format(Date().time)
    val a = SimpleDateFormat("w.yyyy").parse(s)!!.time + week * 7 * 24 * 60 * 60 * 1000
    return SimpleDateFormat("yyyy-MM-dd").format(a)
}

@SuppressLint("SimpleDateFormat")
fun weekEnd(week: Int): String {
    val s = SimpleDateFormat("w.yyyy").format(Date().time)
    val a =
        SimpleDateFormat("w.yyyy").parse(s)!!.time + week * 7 * 24 * 60 * 60 * 1000 + 6 * 24 * 60 * 60 * 1000
    return SimpleDateFormat("yyyy-MM-dd").format(a)
}
