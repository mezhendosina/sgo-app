package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.app.uiEntities.GradesUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import kotlinx.coroutines.flow.StateFlow

interface GradesRepository {
    val grades: StateFlow<List<GradesUiEntity>>

    val years: StateFlow<List<SchoolYear>>
    val terms: StateFlow<List<Term>>

    val selectedYearId: StateFlow<Int>
    val selectedTrimId: StateFlow<Int>


    suspend fun initFilters()

    fun getAboutGrade(subjectId: Int): SubjectPerformance

    suspend fun getGrades()

    suspend fun getSelectedYear(): Int?
    suspend fun setYearId(id: Int)
    suspend fun setTrimId(id: Int)

    suspend fun sortGrades()

}