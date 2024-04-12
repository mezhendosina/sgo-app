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

import com.mezhendosina.sgo.data.SettingsDataStore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class BaseUrlInterceptor @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : Interceptor {

    private var baseUrl = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            settingsDataStore.getValue(SettingsDataStore.REGION_URL).collect {
                if (it != null){
                    baseUrl = it
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        val url = chain.request().url.toString()
        val a = if (baseUrl.isNotEmpty()) {
            newBuilder.url(url.replace("https://localhost/", baseUrl))
                .build()
        } else {
            newBuilder.url(url).build()
        }
        return chain.proceed(a)
    }
}