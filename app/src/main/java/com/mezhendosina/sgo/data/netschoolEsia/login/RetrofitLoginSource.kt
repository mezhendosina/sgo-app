package com.mezhendosina.sgo.data.netschoolEsia.login

import com.mezhendosina.sgo.data.netschoolEsia.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschoolEsia.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.entities.login.GetTokenResponse
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLoginSource
    @Inject
    constructor(
        retrofitConfig: RetrofitConfig,
    ) : BaseRetrofitSource(retrofitConfig), LoginSource {
        private val api = retrofitConfig.loginRetrofit.create(IdentityLoginApi::class.java)

        override suspend fun getToken(deviceCode: Int): GetTokenResponse =
            wrapRetrofitExceptions {
                api.getToken(
                    grantType = "urn:ietf:params:oauth:grant-type:device_code",
                    deviceCode = deviceCode,
                )
            }

        override suspend fun getToken(refreshToken: String): GetTokenResponse =
            wrapRetrofitExceptions {
                api.getToken(
                    grantType = "refresh_token",
                    refreshToken = refreshToken,
                )
            }

        override suspend fun getUsers(): List<UserInfo> =
            wrapRetrofitExceptions {
                api.getUsers()
            }
    }
