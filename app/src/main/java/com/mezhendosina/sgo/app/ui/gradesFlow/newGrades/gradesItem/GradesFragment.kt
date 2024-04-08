package com.mezhendosina.sgo.app.ui.gradesFlow.newGrades.gradesItem

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.FragmentGradesBinding
import com.mezhendosina.sgo.app.ui.gradesFlow.grades.GradeAdapter
import com.mezhendosina.sgo.app.ui.gradesFlow.grades.OnGradeClickListener
import com.mezhendosina.sgo.app.utils.findTopNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GradesFragment : Fragment(R.layout.fragment_grades) {

    private var binding: FragmentGradesBinding? = null

    private val viewModel by viewModels<GradesViewModel>()

    private val gradeAdapter =
        GradeAdapter(
            object : OnGradeClickListener {
                override fun invoke(p1: Int, p2: View) {

                    val navigationExtras =
                        FragmentNavigatorExtras(
                            p2 to getString(R.string.grade_item_details_transition_name),
                        )

                    findTopNavController().navigate(
                        R.id.action_containerFragment_to_gradeItemFragment,
                        bundleOf("LESSON_INDEX" to p1),
                        null,
                        navigationExtras,
                    )
                    Singleton.gradesRecyclerViewLoaded.value = false
                }
            },
        )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGradesBinding.bind(view)

        binding!!.gradesRecyclerView.adapter = gradeAdapter
        binding!!.gradesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        observeGrades()
    }

    private fun observeGrades() {
        viewModel.grades.observe(viewLifecycleOwner) {
            gradeAdapter.grades = it
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}