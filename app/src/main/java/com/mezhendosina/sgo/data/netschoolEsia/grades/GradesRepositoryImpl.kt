package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.app.model.grades.GradeSortType
import com.mezhendosina.sgo.app.uiEntities.GradesUiEntity
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.calendar.CalendarRepository
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradesRepositoryImpl @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val gradesSource: GradesSource,
    private val appSettings: AppSettings,
    private val calendarRepository: CalendarRepository
) : GradesRepository {
    private val _allGrades = MutableStateFlow<List<SubjectTotal>>(emptyList())

    private val _grades = MutableStateFlow<List<GradesUiEntity>>(emptyList())
    override val grades: StateFlow<List<GradesUiEntity>> = _grades

    private val _years = MutableStateFlow<List<SchoolYear>>(emptyList())
    override val years: StateFlow<List<SchoolYear>> = _years

    private val _terms = MutableStateFlow<List<Term>>(emptyList())
    override val terms: StateFlow<List<Term>> = _terms

    private val _selectedYearId = MutableStateFlow(-1)
    override val selectedYearId: StateFlow<Int> = _selectedYearId

    private val _selectedTrimId = MutableStateFlow(-1)
    override val selectedTrimId: StateFlow<Int> = _selectedTrimId

    override suspend fun initFilters() {
        getYears()
        getTerms()
    }


    override suspend fun getAboutGrade(subjectId: Int): SubjectPerformance {
        val currentUser = appSettings.getStudentId()
        return gradesSource.getWhyTotalGrade(currentUser, subjectId, selectedTrimId.value)
    }

    override suspend fun getSelectedYear(): Int? {
        if (_years.value.isEmpty()) return null
        return if (_selectedYearId.value == -1) {
            val yearId = getCurrentYear(_years.value) ?: _years.value[0].id
            _selectedYearId.emit(
                yearId
            )
            yearId
        } else _selectedYearId.value
    }


    private fun getCurrentYear(years: List<SchoolYear>): Int? {
        years.forEach {
            if (calendarRepository.isNowBetween(it.startDate, it.endDate)) {
                return it.id
            }
        }
        return null
    }


    override suspend fun getGrades() {
        withContext(Dispatchers.Main) {
            _allGrades.emit(emptyList())
        }
        val studentId = appSettings.getStudentId()
        val yearId = getSelectedYear() ?: return
        val subjectTotals = gradesSource.getGrades(studentId, yearId)
        _allGrades.emit(
            subjectTotals
        )
        filterGrades()
    }

    override suspend fun setYearId(id: Int) {
        _selectedYearId.emit(id)
        utilsRepository.getSubjects(_selectedYearId.value)
        getTerms()
        getGrades()
    }

    private suspend fun getYears() {
        val getYears = utilsRepository.getYears()
        _years.emit(getYears)
    }

    override suspend fun setTrimId(id: Int) {
        _selectedTrimId.emit(id)
        filterGrades()
    }

    override suspend fun sortGrades() = filterGrades()


    private suspend fun getTerms() {
        val getYearId = getSelectedYear()
        val getTerms = utilsRepository.getTerms(getYearId ?: return)
        _selectedTrimId.emit(
            getTerms.firstOrNull { it.current }?.id ?: getTerms.getOrNull(0)?.id ?: -1
        )
        _terms.emit(getTerms)
    }

    private suspend fun filterGrades() {
        _grades.emit(emptyList())
        val yearId = getSelectedYear()
        val sortType = appSettings.getValue(SettingsDataStore.SORT_GRADES_BY).first()
        val filteredGrades = _allGrades.value.mapNotNull { subjectTotal ->
            val subject =
                utilsRepository.getSubjectById(subjectTotal.subjectId, yearId ?: return)
            val findTermTotal = subjectTotal.termTotals.find {
                it.term.id == _selectedTrimId.value
            }
            val grade = findTermTotal?.avgMark ?: findTermTotal?.mark?.toFloatOrNull()
            return@mapNotNull grade?.let {
                GradesUiEntity(
                    subjectTotal.subjectId,
                    subject?.name ?: "",
                    it.toDouble()
                )
            }
        }.sortedBy {
            when (sortType) {
                GradeSortType.BY_GRADE_VALUE, GradeSortType.BY_GRADE_VALUE_DESC -> it.grade.toString()
                GradeSortType.BY_LESSON_NAME -> it.name
                else -> it.name
            }
        }

        _grades.emit(
            if (sortType == GradeSortType.BY_GRADE_VALUE) filteredGrades.reversed() else filteredGrades
        )
    }
}