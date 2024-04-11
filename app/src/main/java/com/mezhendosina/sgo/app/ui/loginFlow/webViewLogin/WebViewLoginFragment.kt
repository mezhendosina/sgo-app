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

package com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.FragmentWebViewLoginBinding
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.SettingsDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WebViewLoginFragment : Fragment(R.layout.fragment_web_view_login) {
    private var binding: FragmentWebViewLoginBinding? = null

    private val webView =
        LoginWebView {
            findNavController().navigate(
                R.id.action_webViewLoginFragment_to_gosuslugiResultFragment,
                bundleOf("code" to it)
            )
        }

    @Inject
    lateinit var appSettings: AppSettings

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewLoginBinding.bind(view)
        with(binding!!.webView) {
            webViewClient = webView
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.allowFileAccess = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true

        }

        CoroutineScope(Dispatchers.Main).launch {
            appSettings.getValue(SettingsDataStore.REGION_URL).first()
                ?.let { binding!!.webView.loadUrl(it + "authorize/login?mobile") }
        }
    }

    companion object {
        const val URL = "URL"
    }
}
