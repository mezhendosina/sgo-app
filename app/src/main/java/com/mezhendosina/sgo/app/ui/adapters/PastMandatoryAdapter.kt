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

package com.mezhendosina.sgo.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.ItemPastMandatoryBinding
import com.mezhendosina.sgo.data.DateManipulation
import com.mezhendosina.sgo.data.requests.sgo.diary.entities.PastMandatoryEntity

class PastMandatoryAdapter : RecyclerView.Adapter<PastMandatoryAdapter.PastMandatoryViewHolder>() {

    var items: List<PastMandatoryEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PastMandatoryViewHolder(val binding: ItemPastMandatoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastMandatoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPastMandatoryBinding.inflate(inflater, parent, false)
        return PastMandatoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PastMandatoryViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            this.dueDate.text = DateManipulation(item.dueDate).dateFormatter()
            this.homework.text = item.assignmentName
            this.lessonName.text = item.subjectName
        }
    }

    override fun getItemCount(): Int = items.size
}