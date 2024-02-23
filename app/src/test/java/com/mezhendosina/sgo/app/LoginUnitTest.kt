package com.mezhendosina.sgo.app

import com.mezhendosina.sgo.data.netschool.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.login.RetrofitLoginSource
import com.mezhendosina.sgo.di.BaseRetrofit
import org.junit.Test

class LoginUnitTest {
    val baseRetrofit = BaseRetrofit()
    val retrofitLoginSource =
        RetrofitLoginSource(
            RetrofitConfig(
                baseRetrofit.createRetrofit(
                    baseRetrofit.createGson(),
                    baseRetrofit.createOkHttpClient(),
                ),
            ),
        )

    @Test
    fun testGetToken() {
    }
}
