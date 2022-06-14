package com.mezhendosina.sgo.app.ui.login.chooseSchool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.ChooseSchoolFragmentBinding
import com.mezhendosina.sgo.app.factory
import com.mezhendosina.sgo.app.ui.adapters.ChooseSchoolAdapter
import com.mezhendosina.sgo.app.ui.adapters.OnSchoolClickListener
import com.mezhendosina.sgo.app.ui.hideAnimation
import com.mezhendosina.sgo.app.ui.showAnimation
import com.mezhendosina.sgo.data.layouts.schools.SchoolItem

class ChooseSchoolFragment : Fragment() {

    private lateinit var binding: ChooseSchoolFragmentBinding
    private val viewModel: ChooseSchoolViewModel by viewModels { factory() }

    private var loading = MutableLiveData(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadSchools(requireContext(), loading)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChooseSchoolFragmentBinding.inflate(inflater, container, false)

        val schoolAdapter = ChooseSchoolAdapter(object : OnSchoolClickListener {
            override fun invoke(p1: SchoolItem) {
                requireActivity().findNavController(R.id.fragmentContainer).navigate(
                    R.id.action_chooseSchoolFragment_to_loginFragment,
                    bundleOf("schoolId" to p1.schoolId)
                )
            }
        })
        if (!binding.schoolEditText.text.isNullOrEmpty()) {
            schoolAdapter.schools =
                viewModel.findSchool(binding.schoolEditText.text.toString()) ?: emptyList()
        }

        binding.schoolEditText.addTextChangedListener(onTextChanged = { it, _, _, _ ->
            schoolAdapter.schools = viewModel.findSchool(it.toString()) ?: emptyList()

        })

        loading.observe(viewLifecycleOwner) {
            if (it) {
                showAnimation(binding.progressIndicator)
            } else {
                hideAnimation(binding.progressIndicator, View.GONE)
            }
        }

        viewModel.schools.observe(viewLifecycleOwner) {
            schoolAdapter.schools = it
        }

        binding.schoolList.adapter = schoolAdapter
        binding.schoolList.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

}