package com.mezhendosina.sgo.data.netschoolEsia.utils

import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Subject
import com.mezhendosina.sgo.data.netschoolEsia.entities.common.Term
import com.mezhendosina.sgo.data.netschoolEsia.entities.education.SchoolYear
import com.mezhendosina.sgo.data.netschoolEsia.entities.users.UserInfo

interface UtilsSource {
    suspend fun getYears(studentId: Int): List<SchoolYear>

    suspend fun getSubjects(
        studentId: Int,
        yearId: Int,
    ): List<Subject>


    suspend fun getTerms(studentId: Int, yearId: Int): List<Term>

    suspend fun getUsers(): List<UserInfo>
}
