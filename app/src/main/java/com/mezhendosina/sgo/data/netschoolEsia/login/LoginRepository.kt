package com.mezhendosina.sgo.data.netschoolEsia.login


interface LoginRepository {

    suspend fun login()

    suspend fun login(deviceCode: Int)
    suspend fun logout()
}
