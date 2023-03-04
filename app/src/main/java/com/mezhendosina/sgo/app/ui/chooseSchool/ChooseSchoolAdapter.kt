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

package com.mezhendosina.sgo.app.ui.chooseSchool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.ItemSchoolBinding
import com.mezhendosina.sgo.app.model.chooseSchool.SchoolUiEntity

typealias OnSchoolClickListener = (SchoolUiEntity) -> Unit

class ChooseSchoolAdapter(private val onSchoolClickListener: OnSchoolClickListener) :
    RecyclerView.Adapter<ChooseSchoolAdapter.ChooseSchoolViewHolder>(),
    View.OnClickListener {

    var schools = emptyList<SchoolUiEntity>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    class ChooseSchoolViewHolder(val binding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View?) {
        val school = v?.tag as SchoolUiEntity
        onSchoolClickListener(school)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseSchoolViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSchoolBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return ChooseSchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseSchoolViewHolder, position: Int) {
        val school = schools[position]
        with(holder.binding) {
            holder.itemView.tag = school

            schoolCity.text = school.city
            schoolName.text = school.school
        }
    }

    override fun getItemCount(): Int = schools.size


}