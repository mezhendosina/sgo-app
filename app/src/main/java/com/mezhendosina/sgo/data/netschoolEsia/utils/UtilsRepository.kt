package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface UtilsRepository {
    val years: StateFlow<List<FilterUiEntity>>
    val subjects: StateFlow<List<Subject>>

    suspend fun getYears()

    suspend fun getSubjects(yearId: Int): List<Subject>

    suspend fun getTerms(): List<Term>?

    suspend fun getSelectedTrimId(): Term

    suspend fun getSelectedYear(): FilterUiEntity?

    suspend fun setCurrentYear(yearId: Int)

    suspend fun getUsers(): List<UserInfo>
}
