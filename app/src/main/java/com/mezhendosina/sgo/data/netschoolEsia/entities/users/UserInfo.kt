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

package com.mezhendosina.sgo.data.netschoolEsia.entities.users


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("children")
    val children: Any,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isParent")
    val isParent: Boolean,
    @SerializedName("isStaff")
    val isStaff: Boolean,
    @SerializedName("isStudent")
    val isStudent: Boolean,
    @SerializedName("loginName")
    val loginName: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("organizations")
    val organizationInfos: List<OrganizationInfo>
)