package com.mezhendosina.sgo.data.netschoolEsia.login

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    val users: Flow<List<Int>>

    suspend fun login(deviceCode: Int)

    suspend fun login()

    suspend fun getUsers()

    suspend fun logout()
}
