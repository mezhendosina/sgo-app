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

package com.mezhendosina.sgo.app.ui.journalFlow.lessonItem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.ItemWhyGradeBinding
import com.mezhendosina.sgo.app.uiEntities.WhyGradeEntity
import com.mezhendosina.sgo.app.utils.setupGrade
import com.mezhendosina.sgo.app.utils.toGradeType
import javax.inject.Singleton

@Singleton
class WhyGradeAdapter : RecyclerView.Adapter<WhyGradeAdapter.WhyGradeViewHolder>() {

    var grades: List<WhyGradeEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class WhyGradeViewHolder(val binding: ItemWhyGradeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhyGradeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWhyGradeBinding.inflate(inflater, parent, false)

        return WhyGradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WhyGradeViewHolder, position: Int) {
        val grade = grades[position]
        with(holder.binding) {
            this.grade.apply {
                if (grade.mark.dutyMark) {
                    dutyMark.visibility = View.VISIBLE

                } else {
                    dutyMark.visibility = View.INVISIBLE

                    this.setupGrade(
                        holder.itemView.context,
                        grade.mark.mark.toDouble().toGradeType(),
                        grade.mark.mark.toString()
                    )
                }
            }

            if (grade.mark.markComment.isNullOrEmpty())
                this.markComment.visibility = View.GONE
            else {
                markComment.visibility = View.VISIBLE
                markComment.text = grade.mark.markComment
            }
            if (grade.assignmentName != "---Не указана---") {
                gradeText.text = grade.assignmentName
                gradeType.text = grade.type
                gradeType.visibility = View.VISIBLE
            } else {
                gradeText.text = grade.type
                gradeType.visibility = View.GONE
            }

        }
    }

    override fun getItemCount(): Int = grades.size

}