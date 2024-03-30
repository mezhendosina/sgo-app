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