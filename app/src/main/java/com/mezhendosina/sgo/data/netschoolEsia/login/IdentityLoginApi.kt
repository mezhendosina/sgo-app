package com.mezhendosina.sgo.data.netschoolEsia.login

import com.mezhendosina.sgo.data.netschoolEsia.entities.login.GetTokenResponse
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IdentityLoginApi {
    @POST("connect/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Field("grant_type") grantType: String,
        @Field("device_code") deviceCode: Int? = null,
        @Field("refresh_token") refreshToken: String? = null,
        @Field("client_id") clientId: String = "parent-mobile",
        @Field("client_secret") clientSecret: String = "04064338-13df-4747-8dea-69849f9ecdf0",
    ): GetTokenResponse

    @GET("users?v=2")
    suspend fun getUsers(): List<UserInfo>
}