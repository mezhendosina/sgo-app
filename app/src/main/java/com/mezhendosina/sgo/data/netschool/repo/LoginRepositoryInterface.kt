package com.mezhendosina.sgo.data.netschool.repo

import com.mezhendosina.sgo.app.uiEntities.SchoolUiEntity
import com.mezhendosina.sgo.data.netschool.api.login.entities.StudentResponseEntity
import kotlinx.coroutines.flow.StateFlow

interface LoginRepositoryInterface {
    suspend fun findSchool(schoolId: Int): SchoolUiEntity

    fun getSchools(): StateFlow<List<SchoolUiEntity>>

    suspend fun mapSchools(name: String)

    suspend fun login(
        login: String? = null,
        password: String? = null,
        schoolId: Int? = null,
        firstLogin: Boolean = true,
        onOneUser: () -> Unit = {},
        onMoreUser: (List<StudentResponseEntity>) -> Unit = {},
    )

    suspend fun logout()
}
