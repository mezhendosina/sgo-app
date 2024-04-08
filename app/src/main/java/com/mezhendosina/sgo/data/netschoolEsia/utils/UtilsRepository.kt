package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface UtilsRepository {
    val subjects: StateFlow<List<Subject>>

    /**
     * Получает все года, которые существуют у пользователя
     */
    suspend fun getYears(): List<SchoolYear>

    /**
     * Получает предметы в учебном году
     */
    suspend fun getSubjects(yearId: Int)

    /**
     *
     */
    suspend fun getSubjectById(id: Int, yearId: Int): Subject?

    suspend fun getUsers(): List<UserInfo>
    suspend fun getTerms(yearId: Int): List<Term>
}
