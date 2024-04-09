package com.mezhendosina.sgo.data.netschoolEsia.entities.analytics


import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.app.model.grades.entities.CountGradeEntity
import com.mezhendosina.sgo.app.utils.GradesType
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem

data class MarkStat(
    @SerializedName("count")
    val count: Int,
    @SerializedName("fraction")
    val fraction: Double,
    @SerializedName("mark")
    val mark: Double
) {
    fun toCountGradeItem(): CountGradeEntity? {
        if (count == 0) return null
        val name = when (mark) {
            5.0 -> "Пятерок"
            4.0 -> "Четверок"
            3.0 -> "Троек"
            2.0 -> "Двоек"
            else -> mark.toString()
        }
        val gradeType = when (mark) {
            5.0, 4.0 -> GradesType.GOOD_GRADE
            3.0 -> GradesType.MID_GRADE
            2.0 -> GradesType.BAD_GRADE
            else -> GradesType.MID_GRADE
        }
        return CountGradeEntity(
            name,
            mark.toInt(),
            gradeType,
            count
        )
    }
}