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

package com.mezhendosina.sgo.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

@Module
@InstallIn(SingletonComponent::class)
class MyCookieJar : CookieJar {

    private val cookiesList = mutableListOf<Cookie>()


    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookiesList
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookieCopy = cookies.toMutableList()

        cookiesList.replaceAll { oldCookie ->
            val findCookie = cookieCopy.find { it.name == oldCookie.name }
            cookieCopy.remove(findCookie)
            findCookie ?: oldCookie
        }
        cookiesList.addAll(cookieCopy)

    }

    fun toCookieString(): String {
        var cookiesString = ""

        cookiesList.forEach {
            if (it != null) {
                cookiesString += "${it.name}=${it.value}; "
            }
        }
        return cookiesString
    }
}