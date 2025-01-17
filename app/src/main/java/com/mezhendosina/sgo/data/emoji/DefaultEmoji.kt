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


package com.mezhendosina.sgo.data.emoji

import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.uiEntities.LessonNameUiEntity

object DefaultLessons {
    val list =
        listOf(
            LessonNameUiEntity("русский", R.drawable.lesson_russia, "Русский язык"),
            LessonNameUiEntity("математик", R.drawable.lesson_math, "Математика"),
            LessonNameUiEntity("информатик", R.drawable.lesson_inf, "Информатика"),
            LessonNameUiEntity("литератур", R.drawable.lesson_lit, "Литература"),
            LessonNameUiEntity("английск", R.drawable.lesson_eng, "Английский язык"),
            LessonNameUiEntity("иностранн", R.drawable.lesson_languages, "Иностранный язык"),
            LessonNameUiEntity("немецк", R.drawable.lesson_german, "Немецкий"),
            LessonNameUiEntity("алгебр", R.drawable.lesson_math, "Алгебра"),
            LessonNameUiEntity("геометр", R.drawable.lesson_geometry, "Геометрия"),
            LessonNameUiEntity(
                "основы безопасности жизнедеятельности",
                R.drawable.lesson_obz,
                "Основы безопасности жизнедеятельности",
            ),
            LessonNameUiEntity("география", R.drawable.lesson_geo, "География"),
            LessonNameUiEntity("обществознание", R.drawable.lesson_social, "Обществознание"),
            LessonNameUiEntity("история", R.drawable.lesson_history, "История"),
            LessonNameUiEntity(
                "изо",
                R.drawable.lesson_paint,
                "Изобразительное искусство",
            ),
            LessonNameUiEntity("физическая культура", R.drawable.lesson_run, "Физическая культура"),
            LessonNameUiEntity("музыка", R.drawable.lesson_music, "Музыка"),
            LessonNameUiEntity("химия", R.drawable.lesson_chemistry, "Химия"),
            LessonNameUiEntity("физика", R.drawable.lesson_phys, "Физика"),
            LessonNameUiEntity("астрономия", R.drawable.lesson_astronomy, "Астрономия"),
            LessonNameUiEntity("биология", R.drawable.lesson_bio, "Биология"),
            LessonNameUiEntity("технология", R.drawable.lesson_tech, "Технология"),
            LessonNameUiEntity(
                "окружающий мир",
                R.drawable.lesson_natural_science,
                "Окружающий мир",
            ),
            LessonNameUiEntity("проект", R.drawable.lesson_project, null),
        )
}
