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

package com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings


import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Teacher

data class Classmeetings(
    @SerializedName("addEducation")
    val addEducation: Boolean,
    @SerializedName("assignmentId")
    val assignmentId: List<Int>,
    @SerializedName("attachmentsExists")
    val attachmentsExists: Boolean,
    @SerializedName("attendance")
    val attendance: String,
    @SerializedName("classmeetingId")
    val classmeetingId: Int,
    @SerializedName("day")
    val day: String,
    @SerializedName("distanceMeetingId")
    val distanceMeetingId: Any,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("extraActivity")
    val extraActivity: Boolean,
    @SerializedName("lessonTheme")
    val lessonTheme: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("resultsExists")
    val resultsExists: Boolean,
    @SerializedName("roomName")
    val roomName: String,
    @SerializedName("scheduleTimeNumber")
    val scheduleTimeNumber: Int,
    @SerializedName("scheduleTimeRelay")
    val scheduleTimeRelay: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("studentId")
    val studentId: Int,
    @SerializedName("subjectGroupId")
    val subjectGroupId: Int,
    @SerializedName("subjectId")
    val subjectId: Int,
    @SerializedName("subjectName")
    val subjectName: String,
    @SerializedName("teachers")
    val teachers: List<Teacher>
)