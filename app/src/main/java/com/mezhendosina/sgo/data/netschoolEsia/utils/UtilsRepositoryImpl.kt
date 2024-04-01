package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilsRepositoryImpl
@Inject
constructor(
    val utilsSource: UtilsSource,
    val calendarRepository: CalendarRepository,
    val appSettings: AppSettings,
) : UtilsRepository {
    private val _years = MutableStateFlow<List<FilterUiEntity>>(emptyList())
    override val years: StateFlow<List<FilterUiEntity>> = _years

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    override val subjects: StateFlow<List<Subject>> = _subjects

    override suspend fun getYears() {
        val studentId = appSettings.getStudentId()
        val response = utilsSource.getYears(studentId)
        val currYear = getCurrentYear(response)
        _years.emit(response.map {
            it.toUi(it.id == currYear)
        })
    }

    override suspend fun getSubjects(yearId: Int): List<Subject> {
        if (_subjects.value.isEmpty()) {
            val studentId = appSettings.getStudentId()
            _subjects.value =
                utilsSource.getSubjects(
                    studentId,
                    yearId,
                )
        }
        return _subjects.value
    }

    override suspend fun getTerms(): List<Term>? {
        val studentId = appSettings.getStudentId()
        val yearId = getSelectedYear()?.id
        return yearId?.let { utilsSource.getTerms(studentId, it) }
    }

    private fun getCurrentYear(years: List<SchoolYear>): Int? {
        years.forEach {
            if (calendarRepository.isNowBetween(it.startDate, it.endDate)) {
                return it.id
            }
        }
        return null
    }

    override suspend fun getSelectedTrimId(): Term {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedYear(): FilterUiEntity? {
        return _years.value.firstOrNull { it.checked }
    }

    override suspend fun setCurrentYear(yearId: Int) {
        val mapYear = _years.value.map {
            FilterUiEntity(
                it.id,
                it.name,
                it.id == yearId
            )
        }
        withContext(Dispatchers.Main){
            _years.value = mapYear
        }

    }

    override suspend fun getUsers(): List<UserInfo> {
        return utilsSource.getUsers()
    }
}
