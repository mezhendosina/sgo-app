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

data class Result(
    @SerializedName("assignmentId")
    val assignmentId: Int,
    @SerializedName("assignmentTypeAbbr")
    val assignmentTypeAbbr: String,
    @SerializedName("assignmentTypeId")
    val assignmentTypeId: Int,
    @SerializedName("assignmentTypeName")
    val assignmentTypeName: String,
    @SerializedName("classMeetingDate")
    val classMeetingDate: String,
    @SerializedName("classMeetingId")
    val classMeetingId: Int,
    @SerializedName("comment")
    val comment: Any,
    @SerializedName("date")
    val date: String,
    @SerializedName("duty")
    val duty: Boolean,
    @SerializedName("result")
    val result: Double,
    @SerializedName("weight")
    val weight: Int
)