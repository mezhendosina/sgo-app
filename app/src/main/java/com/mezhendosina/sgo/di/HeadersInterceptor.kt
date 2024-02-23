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
class HeadersInterceptor
    @Inject
    constructor(
        private val settingsDataStore: SettingsDataStore,
    ) : Interceptor {
        private var token = ""

        init {
            CoroutineScope(Dispatchers.IO).launch {
                settingsDataStore.getValue(SettingsDataStore.TOKEN).collect {
                    if (it != null) {
                        token = it
                    }
                }
            }
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            val newBuilder = chain.request().newBuilder()
            newBuilder.addHeader("authorization", "Bearer $token")
            return chain.proceed(newBuilder.build())
        }
    }
