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

package com.mezhendosina.sgo.app.ui.gradesFlow.gradeItem

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.GRADE_ID
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.FragmentGradeItemBinding
import com.mezhendosina.sgo.app.utils.GradesType
import com.mezhendosina.sgo.app.utils.ItemOffsetDecoration
import com.mezhendosina.sgo.app.utils.findTopNavController
import com.mezhendosina.sgo.app.utils.getEmojiLesson
import com.mezhendosina.sgo.app.utils.setLessonEmoji
import com.mezhendosina.sgo.app.utils.setupColorWithGrade
import com.mezhendosina.sgo.app.utils.setupGrade
import com.mezhendosina.sgo.app.utils.toGradeType
import com.mezhendosina.sgo.data.netschoolEsia.entities.analytics.SubjectPerformance
import com.mezhendosina.sgo.data.netschoolEsia.entities.grades.GradesItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class GradeItemFragment : Fragment(R.layout.fragment_grade_item) {
    var binding: FragmentGradeItemBinding? = null

    internal val viewModel: GradeItemViewModel by viewModels()

    private val calculateGradeAdapter =
        CalculateGradeAdapter(
            object : ChangeGradeClickListener {
                override fun plusGrade(grade: Int) {
                    viewModel.editGrade(grade, 1)
                }

                override fun minusGrade(grade: Int) {
                    viewModel.editGrade(grade, -1)
                }

                override fun manualEditGrade(
                    grade: Int,
                    value: Int,
                ) {
                    viewModel.editGrade(grade, value)
                }
            },
        )

    private val countGradeAdapter = CountGradeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            MaterialContainerTransform().apply {
                scrimColor = Color.TRANSPARENT
            }
        sharedElementReturnTransition =
            MaterialContainerTransform().apply {
                scrimColor = Color.TRANSPARENT
            }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGradeItemBinding.bind(view)
        val s = arguments?.getInt(GRADE_ID).toString()
        ViewCompat.setTransitionName(binding!!.root, s)

        observeLesson()
        observeCalculatedGrade()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSubject(
                arguments?.getInt(GRADE_ID) ?: return@launch
            )
            withContext(Dispatchers.Main){
                viewModel.initCalculator()
            }

        }
    }

    private fun observeLesson() {
        viewModel.lesson.observe(viewLifecycleOwner) { lesson ->
            if (lesson != null) {
                with(binding!!.gradeToolbar) {
                    collapsingtoolbarlayout.title = lesson.subject.name
                    itemToolbar.setNavigationOnClickListener { findTopNavController().popBackStack() }
                    setLessonEmoji(requireContext(), lesson.subject.name)
                }

                binding!!.gradeCalculator.calculateGrade.adapter = calculateGradeAdapter
                binding!!.gradeCalculator.calculateGrade.layoutManager =
                    LinearLayoutManager(requireContext())
                binding!!.gradeCalculator.calculateGrade.itemAnimator = null

                if (Singleton.gradesWithWeight) {
                    binding!!.gradeCalculator.root.visibility =
                        View.GONE
                }

                countGradeAdapter.countGrades =
                    lesson.markStats.mapNotNull { it.toCountGradeItem() }
                val itemOffsetDecoration = ItemOffsetDecoration(36)
                with(binding!!.countGrade) {
                    adapter = countGradeAdapter
                    layoutManager =
                        GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
                    addItemDecoration(itemOffsetDecoration)
                }

                setupAvgGrade(lesson)
            }
        }
    }

    override fun onDestroyView() {
        binding!!.countGrade.adapter = null
        binding = null
        super.onDestroyView()
    }

    private fun setupAvgGrade(lesson: SubjectPerformance) {
        val avgGradeType = lesson.averageMark.toGradeType()
        binding!!.avgGrade.root.setBackgroundResource(
            when (avgGradeType) {
                GradesType.GOOD_GRADE -> R.drawable.shape_good_grade
                GradesType.MID_GRADE -> R.drawable.shape_mid_grade
                GradesType.BAD_GRADE -> R.drawable.shape_bad_grade
                else -> 0
            },
        )
        binding!!.avgGrade.avgHeader.setupColorWithGrade(requireContext(), avgGradeType)
        binding!!.avgGrade.avgGrade.setupGrade(
            requireContext(),
            avgGradeType,
            lesson.averageMark.toString(),
            true,
        )
        binding!!.avgGrade.root.background.setBounds(41, 41, 41, 41)
    }

    private fun observeCalculatedGrade() {
        viewModel.calculatedGrade.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            calculateGradeAdapter.grades = it.toList()
            calculateGradeAdapter.initGrades =
                viewModel.grade.value?.toList() ?: listOf(0, 0, 0, 0, 0)
            val avgGrade = it.avg()
            binding!!.gradeCalculator.calculatedGrade.setupGrade(
                requireContext(),
                avgGrade.toDouble().toGradeType(),
                avgGrade.toString(),
            )
        }
    }

    companion object {
        const val FIVE_GRADE = 0
        const val FOUR_GRADE = 1
        const val THREE_GRADE = 2
        const val TWO_GRADE = 3
    }
}
