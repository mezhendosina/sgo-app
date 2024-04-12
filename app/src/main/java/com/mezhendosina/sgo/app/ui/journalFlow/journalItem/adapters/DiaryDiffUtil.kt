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

package com.mezhendosina.sgo.app.ui.journalFlow.journalItem.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.model.journal.entities.WeekDayUiEntity
import javax.inject.Singleton


@Singleton
class DiaryDiffUtil(
    private val oldDiary: List<WeekDayUiEntity>,
    private val newDiary: List<WeekDayUiEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldDiary.size

    override fun getNewListSize(): Int = newDiary.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDiary[oldItemPosition].date == newDiary[newItemPosition].date

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDiary[oldItemPosition] == newDiary[newItemPosition]
}