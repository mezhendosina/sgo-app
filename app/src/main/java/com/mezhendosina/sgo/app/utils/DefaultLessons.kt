/*
 * Copyright 2023 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mezhendosina.sgo.app.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.transition.TransitionManager
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.ItemLessonToolbarBinding
import com.mezhendosina.sgo.app.uiEntities.LessonNameUiEntity
import com.mezhendosina.sgo.data.emoji.DefaultLessons
import kotlin.math.abs

fun getEmojiLesson(lessonName: String): LessonNameUiEntity? {
    return DefaultLessons.list.firstOrNull { lessonName.contains(it.nameId, true) }
}

fun ImageView.setupAsLessonEmoji(
    context: Context,
    lessonName: String,
) {
    val bitmap =
        AppCompatResources.getDrawable(
            context,
            getEmojiLesson(lessonName)?.emoji ?: R.drawable.ic_question,
        )?.toBitmap()

    val defaultColor = TypedValue()
    context.theme.resolveAttribute(
        com.google.android.material.R.attr.colorPrimaryContainer,
        defaultColor,
        true,
    )
    if (bitmap != null) {
        setImageBitmap(bitmap)
        val palette = Palette.from(bitmap).generate()
        val vibrantColor = palette.getVibrantColor(defaultColor.data)
        background.setTint(vibrantColor)
        background.alpha = 28
    }
}

fun ItemLessonToolbarBinding.setLessonEmoji(
    context: Context,
    lessonName: String?,
) {
    if (lessonName != null) {
        expandedIcon.setupAsLessonEmoji(context, lessonName)
        collapsedIcon.setupAsLessonEmoji(context, lessonName)
//        if (collapsingtoolbarlayout.lineCount > 1) {
//
//        }
        val fadeTransition = com.google.android.material.transition.MaterialFadeThrough()
        this.appbarlayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            TransitionManager.beginDelayedTransition(this.root, fadeTransition)
            collapsedIcon.visibility =
                if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
        }
    } else {
        expandedIcon.visibility = View.GONE
        collapsingtoolbarlayout.expandedTitleMarginStart =
            collapsingtoolbarlayout.expandedTitleMarginStart
        collapsedIcon.visibility = View.GONE
    }
}

