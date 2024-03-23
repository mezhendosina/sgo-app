package com.mezhendosina.sgo.di

import android.webkit.WebViewClient
import com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin.LoginWebView
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WebViewClientModule {
    @Binds
    abstract fun bindsWebViewClient(webViewClient: LoginWebView): WebViewClient
}
