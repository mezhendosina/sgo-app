/*
 * Copyright 2023 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mezhendosina.sgo.data.netschoolEsia.diary

import com.mezhendosina.sgo.data.netschoolEsia.entities.announcements.AttachmentEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.assignments.Assignment
import com.mezhendosina.sgo.data.netschoolEsia.entities.classmeetings.Classmeetings
import retrofit2.http.GET
import retrofit2.http.Query

interface DiaryApi {
    @GET("api/mobile/classmeetings")
    suspend fun getDiary(
        @Query("studentIds") studentId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("extraActivity") extraActivity: Boolean? = null,
    ): List<Classmeetings>

    @GET("api/mobile/assignments")
    suspend fun getAssignments(
        @Query("studentId") studentId: Int,
        @Query("classmeetingId") lessonId: Int,
    ): List<Assignment>

    suspend fun getAttachment(
        @Query("assignmentId") assignmentId: Int,
    ): List<AttachmentEntity>
}
