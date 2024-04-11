package com.mezhendosina.sgo.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.color.DynamicColors
import com.mezhendosina.sgo.app.BuildConfig
import com.mezhendosina.sgo.data.AppSettings
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsDataStore: AppSettings

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        runBlocking {
            AppCompatDelegate.setDefaultNightMode(
                settingsDataStore.getValue(SettingsDataStore.THEME).first()
                    ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            )
        }
        if (!BuildConfig.DEBUG) DynamicColors.applyToActivitiesIfAvailable(application)
//        DynamicColors.applyToActivitiesIfAvailable(this.application)

        CoroutineScope(Dispatchers.Main).launch {
//            FirebaseApp.initializeApp(this@RoutingActivity)

            val intent =
                if (!settingsDataStore.getValue(SettingsDataStore.REFRESH_TOKEN).first()
                        .isNullOrEmpty()
                ) {
                    Intent(this@RoutingActivity, MainActivity::class.java)
                } else {
                    Intent(this@RoutingActivity, LoginActivity::class.java)
                }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
