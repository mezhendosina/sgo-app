package com.mezhendosina.sgo.app.model.grades

import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions.GradeOptions
import kotlinx.coroutines.flow.StateFlow

interface GradesRepositoryInterface {
    val selectedGradesItem: StateFlow<GradesItem?>

    suspend fun loadGrades(gradeOptions: GradeOptions, termid: String, sortType: Int)

    suspend fun loadGradesOptions(): GradeOptions

    fun setSelectedGradesItem(gradesItem: GradesItem)

    fun addListener(listener: GradeActionListener)

    fun removeListener(listener: GradeActionListener)
}