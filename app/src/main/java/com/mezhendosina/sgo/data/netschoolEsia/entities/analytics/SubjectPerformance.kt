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
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Teacher
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term

data class SubjectPerformance(
    @SerializedName("attendance")
    val attendance: List<Attendance>,
    @SerializedName("averageMark")
    val averageMark: Double,
    @SerializedName("classAverageMark")
    val classAverageMark: Double,
    @SerializedName("classmeetingsStats")
    val classmeetingsStats: ClassmeetingsStats,
    @SerializedName("markStats")
    val markStats: List<MarkStat>,
    @SerializedName("maxMark")
    val maxMark: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("subject")
    val subject: Subject,
    @SerializedName("teachers")
    val teachers: List<Teacher>,
    @SerializedName("term")
    val term: Term,
)
