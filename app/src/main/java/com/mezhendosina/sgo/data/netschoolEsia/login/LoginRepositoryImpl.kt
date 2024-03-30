package com.mezhendosina.sgo.data.netschoolEsia.login

import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mezhendosina.sgo.app.ui.loginFlow.chooseRegion.entities.ChooseRegionUiEntity
import com.mezhendosina.sgo.app.uiEntities.SchoolUiEntity
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschool.api.login.LoginEntity
import com.mezhendosina.sgo.data.netschool.api.login.entities.StudentResponseEntity
import com.mezhendosina.sgo.data.netschool.api.login.entities.accountInfo.User
import com.mezhendosina.sgo.data.netschool.api.regions.Regions
import com.mezhendosina.sgo.data.netschool.repo.LoginRepository
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import com.mezhendosina.sgo.data.requests.sgo.login.entities.LoginResponseEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl
@Inject
constructor(
    val settingsDataStore: SettingsDataStore,
    val loginSource: LoginSource,
    @ApplicationContext val context: Context
) : LoginRepository {
    override suspend fun login(deviceCode: Int) {
        val loginResponse = loginSource.getToken(deviceCode)
        settingsDataStore.saveToken(
            loginResponse.accessToken,
            loginResponse.refreshToken,
        )
    }

    override suspend fun login() {
        val token = settingsDataStore.getValue(SettingsDataStore.REFRESH_TOKEN)
        val newTokens = token.first()?.let { loginSource.getToken(it) }
        newTokens?.let {
            settingsDataStore.saveToken(
                it.accessToken,
                it.refreshToken
            )
        }
    }

    override suspend fun getUsers(): List<UserInfo> {
        return loginSource.getUsers()
    }

    override suspend fun getRegions(): ChooseRegionUiEntity {
        return Gson().fromJson(
            Regions.REGIONS,
            ChooseRegionUiEntity::class.java,
        )
    }

    private val _schools = MutableStateFlow<List<SchoolUiEntity>>(emptyList())

    override suspend fun findSchool(schoolId: Int): SchoolUiEntity {
        return _schools.last().first { it.id == schoolId }
    }

    override fun getSchools(): StateFlow<List<SchoolUiEntity>> {
        return _schools
    }

    override suspend fun mapSchools(name: String) {
    }

    override suspend fun login(
        login: String?,
        password: String?,
        schoolId: Int?,
        firstLogin: Boolean,
        onOneUser: () -> Unit,
        onMoreUser: (List<StudentResponseEntity>) -> Unit,
    ) {
    }

    override suspend fun gosuslugiLogin(b: Boolean) = Unit

    override suspend fun getGosuslugiUsers(loginState: String): List<User> = emptyList()

    private suspend fun postLogin(
        loginEntity: LoginEntity,
        loginRequest: LoginResponseEntity,
        firstLogin: Boolean,
        onOneUser: () -> Unit,
        onMoreUser: (List<StudentResponseEntity>) -> Unit,
    ) {
    }

    override suspend fun logout() = Unit
}
