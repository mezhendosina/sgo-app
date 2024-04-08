package com.mezhendosina.sgo.app

import android.content.Context
import com.mezhendosina.sgo.app.model.grades.GradeSortType
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity

const val CLASSMEETING_ID = "classmeeting_id"
const val TERM_ID = "term_id"
const val DIARY_RECYCLERVIEW_INITIAL_ITEMS_COUNT = 6

fun sortGradesList(context: Context, selectedItem: Int): List<FilterUiEntity> = listOf(
    FilterUiEntity(
        GradeSortType.BY_GRADE_VALUE,
        GradeSortType.toString(context, GradeSortType.BY_GRADE_VALUE),
        selectedItem == GradeSortType.BY_GRADE_VALUE
    ),
    FilterUiEntity(
        GradeSortType.BY_GRADE_VALUE_DESC,
        GradeSortType.toString(
            context,
            GradeSortType.BY_GRADE_VALUE_DESC
        ),
        selectedItem == GradeSortType.BY_GRADE_VALUE_DESC
    ),
    FilterUiEntity(
        GradeSortType.BY_LESSON_NAME,
        GradeSortType.toString(context, GradeSortType.BY_LESSON_NAME),
        selectedItem == GradeSortType.BY_LESSON_NAME
    ),
)