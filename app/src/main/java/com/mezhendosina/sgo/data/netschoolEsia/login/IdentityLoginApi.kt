/*
 * Copyright 2024 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
