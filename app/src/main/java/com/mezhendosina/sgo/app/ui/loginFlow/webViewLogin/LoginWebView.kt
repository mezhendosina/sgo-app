package com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin

import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

data class UserCode(val userCode: Int)

class LoginWebView(
    val onLoggedIn: (userCode: Int) -> Unit,
) : WebViewClient() {
    private val gson = Gson()


    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.i("WebView", "shouldOverrideUrlLoading: ${request?.url?.query}")
        if (request?.url?.scheme == "irtech" && request.url?.query?.contains("pincode") == true) {
            val code = request.url.getQueryParameter("pincode")?.toIntOrNull()
            code?.let { onLoggedIn(it) }
        }
        return false
    }
}
