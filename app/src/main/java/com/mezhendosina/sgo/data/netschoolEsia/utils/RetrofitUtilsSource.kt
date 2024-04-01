package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.base.BaseRetrofitSource
import com.mezhendosina.sgo.data.netschoolEsia.base.RetrofitConfig
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
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
            utilsApi.getEducation(studentId).filter { !it.isAddSchool }.map { it.schoolYear }
        }

    override suspend fun getSubjects(
        studentId: Int,
        yearId: Int,
    ) = wrapRetrofitExceptions {
        utilsApi.getSubjects(studentId, yearId)
    }

    override suspend fun getTerms(studentId: Int, yearId: Int): List<Term> =
        wrapRetrofitExceptions {
            utilsApi.getTerms(studentId, yearId)
        }

    override suspend fun getUsers(): List<UserInfo> = wrapRetrofitExceptions {
        utilsApi.getUsers()
    }
}
