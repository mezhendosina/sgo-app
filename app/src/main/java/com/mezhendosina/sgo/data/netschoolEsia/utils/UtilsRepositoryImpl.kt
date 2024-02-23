package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UtilsRepositoryImpl
    @Inject
    constructor(
        val utilsSource: UtilsSource,
        val appSettings: AppSettings,
    ) : UtilsRepository {
        private val _years = MutableStateFlow<List<SchoolYear>>(emptyList())
        override val years: StateFlow<List<SchoolYear>> = _years

        private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
        override val subjects: StateFlow<List<Subject>> = _subjects

        override suspend fun getYears() {
            val studentId = appSettings.getStudentId()
            val response = utilsSource.getYears(studentId)

            _years.emit(response)
        }

        override suspend fun getSubjects() {
            if (_years.value.isEmpty()) getYears()
        }

        override suspend fun getCurrentYear(): SchoolYear {
            TODO("Not yet implemented")
        }

        override suspend fun setCurrentYear(yearId: Int) {
            TODO("Not yet implemented")
        }
    }
