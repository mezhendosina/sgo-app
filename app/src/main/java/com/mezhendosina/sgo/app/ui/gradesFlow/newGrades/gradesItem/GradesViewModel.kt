package com.mezhendosina.sgo.app.ui.gradesFlow.newGrades.gradesItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.ui.gradesFlow.grades.GradeAdapter
import com.mezhendosina.sgo.app.ui.gradesFlow.grades.OnGradeClickListener
import com.mezhendosina.sgo.app.uiEntities.GradesUiEntity
import com.mezhendosina.sgo.app.utils.BaseViewModel
import com.mezhendosina.sgo.app.utils.LoadStates
import com.mezhendosina.sgo.app.utils.toDescription
import com.mezhendosina.sgo.data.netschoolEsia.grades.GradesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class GradesViewModel @Inject constructor(
    private val gradesRepository: GradesRepository,
) : BaseViewModel() {

    private val _grades = MutableLiveData<List<GradesUiEntity>>()
    val grades: LiveData<List<GradesUiEntity>> = _grades

    var gradeAdapter: GradeAdapter? = null

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun setAdapter(onClickListener: OnGradeClickListener) {
        gradeAdapter = GradeAdapter(onClickListener)
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            gradesRepository.initFilters()
        }
        viewModelScope.launch {
            gradesRepository.grades.collect { subjectTotals ->
                _grades.value = subjectTotals
            }
        }
    }

    suspend fun load() {
        try {
            gradesRepository.getGrades()
            withContext(Dispatchers.Main) {
                Singleton.updateGradeState.value = LoadStates.FINISHED
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e(null, e.stackTraceToString())
                _errorMessage.value = e.toDescription()
                Singleton.updateGradeState.value = LoadStates.ERROR
            }
        }
    }
}