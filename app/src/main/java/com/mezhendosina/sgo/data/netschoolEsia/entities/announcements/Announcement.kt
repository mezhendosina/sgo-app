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

package com.mezhendosina.sgo.data.netschoolEsia.entities.announcements


import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Author

data class Announcement(
    @SerializedName("announcementId")
    val announcementId: Int,
    @SerializedName("attachments")
    val attachmentEntities: List<AttachmentEntity>,
    @SerializedName("author")
    val author: Author,
    @SerializedName("description")
    val description: String,
    @SerializedName("organizations")
    val organizations: List<Organization>,
    @SerializedName("postDate")
    val postDate: String,
    @SerializedName("recipients")
    val recipients: List<String>,
    @SerializedName("title")
    val title: String
)