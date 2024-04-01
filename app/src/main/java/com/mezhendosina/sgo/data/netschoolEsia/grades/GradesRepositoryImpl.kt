package com.mezhendosina.sgo.data.netschoolEsia.grades

import com.mezhendosina.sgo.app.model.grades.GradeActionListener
import com.mezhendosina.sgo.app.model.grades.GradesRepositoryInterface
import com.mezhendosina.sgo.data.SettingsDataStore
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions.GradeOptions
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions.InputTag
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.gradeOptions.SelectTag
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradesRepositoryImpl
@Inject
constructor(
    val gradesSource: GradesSource,
    val settingsDataStore: SettingsDataStore,
    val utilsSource: UtilsRepository,
) : GradesRepositoryInterface {
    private var grades = mutableListOf<GradesItem>()
    private val listeners = mutableSetOf<GradeActionListener>()

    private val _selectedGradesItem: MutableStateFlow<GradesItem?> = MutableStateFlow(null)

    override val selectedGradesItem: StateFlow<GradesItem?> = _selectedGradesItem
    private val mutex = Mutex()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            utilsSource.getYears()
        }

    }

    override suspend fun loadGrades(
        gradeOptions: GradeOptions,
        termid: String,
        sortType: Int,
    ) {
        grades.clear()

        val studentId = settingsDataStore.getStudentId()
        val yearId = utilsSource.getSelectedYear() ?: return

        val lessons = utilsSource.getSubjects(yearId.id)
        val resp = gradesSource.getGrades(studentId, yearId.id)

        resp.forEach { subjectTotal ->
            val findLesson = lessons.firstOrNull { subject -> subject.id == subjectTotal.subjectId }
            withContext(Dispatchers.IO) {
//                val getAboutGrade =
//                    gradesSource.getWhyTotalGrade(
//                        studentId,
//                        subjectTotal.subjectId,
//                        termid.toInt(),
//                    )
//                val getFives = getAboutGrade.markStats.firstOrNull { it.mark == 5.toDouble() }
//                val getFourths = getAboutGrade.markStats.firstOrNull { it.mark == 4.toDouble() }
//                val getThrees = getAboutGrade.markStats.firstOrNull { it.mark == 3.toDouble() }
//                val getTwos = getAboutGrade.markStats.firstOrNull { it.mark == 2.toDouble() }
//                val getOne = getAboutGrade.markStats.firstOrNull { it.mark == 1.toDouble() }
                val termTotal = subjectTotal.termTotals.find { it.term.id == termid.toInt() }
                if (termTotal != null) {
                    val avgMark = termTotal.mark?.toFloatOrNull() ?: termTotal.avgMark
                    if (avgMark != null) {
                        withContext(Dispatchers.Main) {
                            synchronized(mutex) {
                                grades.add(
                                    GradesItem(
                                        findLesson?.name ?: "",
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
//                            getFives?.count,
//                            getFourths?.count,
//                            getThrees?.count,
//                            getTwos?.count,
//                            getOne?.count,
                                        avgMark.toString(),
                                    ),
                                )
                                notifyListeners()
                            }
                        }
                    }
                }
            }
        }
    }

    override suspend fun loadGradesOptions(): GradeOptions {
        val terms = utilsSource.getTerms()?.map {
            SelectTag(
                it.current,
                it.name,
                it.id.toString()
            )
        }
        return GradeOptions(
            InputTag("", ""), emptyList(), InputTag("", ""),
            terms ?: emptyList()
        )
    }

    override fun setSelectedGradesItem(gradesItem: GradesItem) {
        _selectedGradesItem.value = gradesItem
    }

    override fun addListener(listener: GradeActionListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: GradeActionListener) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it.invoke(grades) }
    }
}
