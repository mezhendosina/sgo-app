package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.totals.SubjectTotal
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradesRepositoryImpl
    @Inject
    constructor(
        val gradesSource: GradesSource,
        val settingsDataStore: SettingsDataStore,
        val utilsSource: UtilsRepository,
    ) : GradesRepository {
        private val _grades = MutableStateFlow<List<SubjectTotal>>(emptyList())
        override val grades: StateFlow<List<SubjectTotal>> = _grades

        override suspend fun getGrades() {
            val studentId = settingsDataStore.getStudentId()
            val yearId = utilsSource.getCurrentYear()
            val response = gradesSource.getGrades(studentId, yearId.id)
            _grades.emit(response)
        }

        override suspend fun getAboutGrade(
            subjectId: Int,
            trimId: Int,
        ): SubjectPerformance {
            return gradesSource.getWhyTotalGrade(
                settingsDataStore.getStudentId(),
                subjectId,
                trimId,
            )
        }
    }
