package com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
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
            println(it)
        }

    @Inject
    lateinit var appSettings: AppSettings

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewLoginBinding.bind(view)
        with(binding!!.root){
            webViewClient = webView
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
        }
        CoroutineScope(Dispatchers.Main).launch {
            appSettings.getValue(SettingsDataStore.REGION_URL).first()
                ?.let { binding!!.root.loadUrl(it) }
        }
    }

    companion object {
        const val URL = "URL"
    }
}
