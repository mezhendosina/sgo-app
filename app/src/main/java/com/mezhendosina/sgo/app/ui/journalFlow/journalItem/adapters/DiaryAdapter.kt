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


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.ItemDiaryBinding
import com.mezhendosina.sgo.app.model.journal.entities.WeekDayUiEntity
import dagger.Module
import javax.inject.Singleton


@Singleton
class DiaryAdapter(
    private val onHomeworkClickListener: OnHomeworkClickListener,
) : RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    var weekDays: List<WeekDayUiEntity> = emptyList()
        set(newValue) {
            val diff = DiaryDiffUtil(weekDays, newValue)
            val res = DiffUtil.calculateDiff(diff)
            field = newValue
            res.dispatchUpdatesTo(this)
        }


    class ViewHolder(
        val binding: ItemDiaryBinding,
        onHomeworkClickListener: OnHomeworkClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val homeworkAdapter = HomeworkAdapter(onHomeworkClickListener)

        val layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val sharedPool = RecyclerView.RecycledViewPool()

        init {
            binding.homeworkRecyclerView.layoutManager = layoutManager
            binding.homeworkRecyclerView.apply {
                adapter = homeworkAdapter
                layoutManager = layoutManager
                setRecycledViewPool(sharedPool)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiaryBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, onHomeworkClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diaryItem = weekDays[position]
        holder.binding.day.text = diaryItem.date
        holder.homeworkAdapter.lessons = diaryItem.lessons
    }

    override fun getItemCount(): Int = weekDays.size
}

