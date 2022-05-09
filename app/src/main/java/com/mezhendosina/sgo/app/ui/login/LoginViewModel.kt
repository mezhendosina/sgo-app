package com.mezhendosina.sgo.app.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.activities.MainActivity
import com.mezhendosina.sgo.app.databinding.LoginFragmentBinding
import com.mezhendosina.sgo.app.ui.errorDialog
import com.mezhendosina.sgo.app.ui.hideAnimation
import com.mezhendosina.sgo.app.ui.showAnimation
import com.mezhendosina.sgo.data.ErrorResponse
import com.mezhendosina.sgo.data.Settings
import com.mezhendosina.sgo.data.SettingsLoginData
import com.mezhendosina.sgo.data.toMD5
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    fun onClickLogin(binding: LoginFragmentBinding, context: Context) {
        binding.fab.hide()

        showAnimation(binding.progressIndicator)

        CoroutineScope(Dispatchers.IO).launch {
            val password = binding.passwordTextField.editText?.text.toString().toMD5()
            val settingsLoginData = SettingsLoginData(
                "2",
                "1",
                "-1",
                "1",
                "2",
                "89",
                binding.loginTextField.editText?.text.toString(),
                password
            )
            try {
                val settings = Settings(context)
                val singleton = Singleton

                singleton.login(settingsLoginData)
                val userId = singleton.requests.diaryInit(singleton.at).currentStudentId
                settings.setCurrentUserId(userId)

                singleton.requests.logout(singleton.at)

                withContext(Dispatchers.Main) {
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                }

                settings.saveALl(settingsLoginData)
            } catch (e: ResponseException) {
                withContext(Dispatchers.Main) {
                    errorDialog(context, e.response.body<ErrorResponse>().message)
                    binding.fab.show()
                    hideAnimation(binding.progressIndicator)
                }
            }

        }

    }
}

