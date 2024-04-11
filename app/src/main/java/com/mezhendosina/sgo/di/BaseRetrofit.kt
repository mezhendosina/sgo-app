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

import com.google.gson.Gson
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.di.qualifier.BaseRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseRetrofit {

    @Provides
    @Singleton
    @BaseRetrofit
    fun createRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://localhost/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun createGson(): Gson = Gson()

    @Provides
    @Singleton
    fun createOkHttpClient(
        baseUrlInterceptor: BaseUrlInterceptor,
        headersInterceptor: HeadersInterceptor,
        loggingInterceptor: Interceptor,
    ): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return OkHttpClient.Builder()
            .addInterceptor(baseUrlInterceptor)
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun createHeadersInterceptor(settingsDataStore: SettingsDataStore): HeadersInterceptor = HeadersInterceptor(settingsDataStore)

    @Provides
    @Singleton
    fun createBaseUrlInterceptor(settingsDataStore: SettingsDataStore): BaseUrlInterceptor = BaseUrlInterceptor(settingsDataStore)

    @Provides
    @Singleton
    fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
