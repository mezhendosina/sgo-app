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

package com.mezhendosina.sgo.data.netschoolEsia.entities.homework

import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.data.netschoolEsia.entities.attachments.Attachment

// TODO to ui entity
data class AssignResponseEntity(
    @SerializedName("activityName")
    val activityName: Any,
    @SerializedName("assignmentName")
    val assignmentName: String,
    @SerializedName("attachments")
    val attachments: List<Attachment>,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("problemName")
    val problemName: Any,
    @SerializedName("productId")
    val productId: Any,
    @SerializedName("subjectGroup")
    val subjectGroup: SubjectGroup,
    @SerializedName("teachers")
    val teachers: List<Teacher>,
    @SerializedName("weight")
    val weight: Int,
)

data class SubjectGroup(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)

data class Teacher(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)
