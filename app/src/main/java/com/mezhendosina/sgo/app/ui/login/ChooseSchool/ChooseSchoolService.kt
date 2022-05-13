package com.mezhendosina.sgo.app.ui.login.ChooseSchool

import com.mezhendosina.sgo.data.schools
import com.mezhendosina.sgo.data.schools.SchoolItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias schoolsActionListener = (List<SchoolItem>) -> Unit

class ChooseSchoolService {

    var schools = mutableListOf<SchoolItem>()

    private val listeners = mutableSetOf<schoolsActionListener>()

    suspend fun loadSchools() {
        val schoolsList = schools()
        withContext(Dispatchers.Main) {
            schools = schoolsList.schoolItems.toMutableList()
            notifyListeners()
        }
    }

    fun addListener(listener: schoolsActionListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: schoolsActionListener) {
        listeners.remove(listener)
    }

    fun notifyListeners() {
        listeners.forEach { it.invoke(schools) }
    }
}