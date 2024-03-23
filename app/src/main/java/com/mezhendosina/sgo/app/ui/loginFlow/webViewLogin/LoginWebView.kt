package com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin

import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import java.net.URL

data class UserCode(val userCode: Int)

class LoginWebView(
    val onLoggedIn: (userCode: Int) -> Unit,
) : WebViewClient() {
    private val gson = Gson()

    override fun onLoadResource(
        view: WebView?,
        url: String?,
    ) {
        super.onLoadResource(view, url)
        if (url?.contains("/webapi/mysettings/mobile/pincode") == true) {
            val conn = URL(url).openConnection()
            conn.connect()
            val inputStream = conn.getInputStream()
            val out = gson.fromJson(inputStream.reader(), UserCode::class.java)
            onLoggedIn(out.userCode)
        }
    }
}
