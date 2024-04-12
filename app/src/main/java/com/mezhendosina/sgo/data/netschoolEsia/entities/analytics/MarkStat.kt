/*
 * Copyright 2024 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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