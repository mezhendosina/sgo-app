package com.mezhendosina.sgo.app.uiEntities

import com.mezhendosina.sgo.data.grades.CalculateGradeItem

data class AboutGradeUiEntity(
    val name: String,
    val avg: Double,
    val calculateGradeItem: CalculateGradeItem
)