package com.mezhendosina.sgo.data.grades

import com.mezhendosina.sgo.app.ui.itemGrade.GradeItemFragment
import com.mezhendosina.sgo.data.requests.grades.entities.GradesItem
import kotlin.math.roundToInt

data class CalculateGradeItem(
    val countFive: Int,
    val countFour: Int,
    val countThree: Int,
    val countTwo: Int,
) {
    private fun count(): Int = countFive + countFour + countThree + countTwo
    fun avg(): Float =
        (countFive * 5 + countFour * 4 + countThree * 3 + countTwo * 2).toFloat() / count().toFloat()

    fun changeGrade(grade: Int, delta: Int): CalculateGradeItem {
        println(grade)
        return when (grade) {
            GradeItemFragment.FIVE_GRADE -> CalculateGradeItem(
                countFive + delta,
                countFour,
                countThree,
                countTwo
            )
            GradeItemFragment.FOUR_GRADE -> CalculateGradeItem(
                countFive,
                countFour + delta,
                countThree,
                countTwo
            )
            GradeItemFragment.THREE_GRADE -> CalculateGradeItem(
                countFive,
                countFour,
                countThree + delta,
                countTwo
            )
            GradeItemFragment.TWO_GRADE -> CalculateGradeItem(
                countFive,
                countFour,
                countThree,
                countTwo + delta
            )
            else -> this
        }
    }

    fun toList(): MutableList<Int> = mutableListOf(countFive, countFour, countThree, countTwo)
    fun toGradeItem(): GradesItem =
        GradesItem(
            "",
            countFive,
            countFour,
            countThree,
            countTwo,
            0,
            avg().toString().replace(".", ",")
        )
}


class GradesCalculator(
    private val gradesItem: GradesItem
) {
    fun autoCalculateGrade(
        changeTo: Float
    ): CalculateGradeItem {
        val changeGradeItem = mutableListOf(0, 0, 0)
        if (changeTo <= 2.5f) {
            changeGradeItem[0] = doCalc(FIVE_GRADE, changeTo)
            changeGradeItem[1] = doCalc(FOUR_GRADE, changeTo)
            changeGradeItem[2] = doCalc(THREE_GRADE, changeTo)
        } else if (changeTo <= 4.0f) {
            changeGradeItem[0] = doCalc(FIVE_GRADE, changeTo)
            changeGradeItem[1] = doCalc(FOUR_GRADE, changeTo)
        } else if (changeTo > 4.0f)
            changeGradeItem[0] = doCalc(FIVE_GRADE, changeTo)

        return CalculateGradeItem(
            changeGradeItem[0],
            changeGradeItem[1],
            changeGradeItem[2],
            gradesItem.two ?: 0
        )
    }

    // formula created by https://github.com/0ladyshek
    private fun doCalc(i: Int, changeTo: Float): Int {
        return ((gradesItem.countGrades() * (gradesItem.avgGrade() - changeTo)) / (changeTo - i)).roundToInt()
    }

    companion object {
        const val FIVE_GRADE = 5
        const val FOUR_GRADE = 4
        const val THREE_GRADE = 3
    }

}


