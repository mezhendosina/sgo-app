package com.mezhendosina.sgo.data.netschoolEsia.login

import com.mezhendosina.sgo.app.ui.loginFlow.chooseRegion.entities.ChooseRegionUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo

interface LoginRepository {
    suspend fun login(deviceCode: Int)

    suspend fun login()

    suspend fun getUsers(): List<UserInfo>

    suspend fun getRegions(): ChooseRegionUiEntity

    suspend fun logout()
}
