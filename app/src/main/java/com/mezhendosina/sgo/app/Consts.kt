/*
 * Copyright 2024 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mezhendosina.sgo.app

import android.content.Context
import com.mezhendosina.sgo.app.model.grades.GradeSortType
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity

const val CLASSMEETING_ID = "classmeeting_id"
const val TERM_ID = "term_id"
const val GRADE_ID = "grade_id"
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