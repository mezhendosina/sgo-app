package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface UtilsApi {
    @GET("api/mobile/education")
    suspend fun getEducation(
        @Query("studentId") studentId: Int,
    ): List<SchoolYear>

    @GET("api/mobile/subjects")
    suspend fun getSubjects(
        @Query("studentId") studentId: Int,
        @Query("schoolYearId") yearId: Int,
    ): List<Subject>

    @GET("api/mobile/users?v=2")
    suspend fun getUsers(): List<UserInfo>
}
