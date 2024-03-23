package com.mezhendosina.sgo.data.netschool.repo

import com.mezhendosina.sgo.app.ui.loginFlow.chooseRegion.entities.ChooseRegionUiEntity
import com.mezhendosina.sgo.app.uiEntities.SchoolUiEntity
import com.mezhendosina.sgo.data.netschool.api.login.entities.StudentResponseEntity
import com.mezhendosina.sgo.data.netschool.api.login.entities.accountInfo.User
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface LoginRepository {
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

    suspend fun gosuslugiLogin(b: Boolean)

    suspend fun getGosuslugiUsers(loginState: String): List<User>

    suspend fun logout()

    suspend fun login(deviceCode: Int)

    suspend fun login()

    suspend fun getUsers(): List<UserInfo>

    suspend fun getRegions(): ChooseRegionUiEntity
}
