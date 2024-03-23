package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschoolEsia.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUtilsSource
    @Inject
    constructor(
        config: RetrofitConfig,
    ) : UtilsSource, BaseRetrofitSource(config) {
        private val utilsApi = config.baseRetrofit.create(UtilsApi::class.java)

        override suspend fun getYears(studentId: Int) =
            wrapRetrofitExceptions {
                utilsApi.getEducation(studentId)
            }

        override suspend fun getSubjects(
            studentId: Int,
            yearId: Int,
        ) = wrapRetrofitExceptions {
            utilsApi.getSubjects(studentId, yearId)
        }
    }
