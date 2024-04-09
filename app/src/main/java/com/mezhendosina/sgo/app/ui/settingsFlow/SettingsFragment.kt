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

package com.mezhendosina.sgo.app.ui.settingsFlow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.activities.LoginActivity
import com.mezhendosina.sgo.app.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getMySettings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


        viewModel.loadProfilePhoto(
            requireContext(),
            binding.profileCard.userPhoto
        )


        binding.changeTheme.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_themeFragment)
        }


        binding.aboutApp.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutAppFragment)
        }

        binding.logoutButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.logout()
                withContext(Dispatchers.Main) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    ContextCompat.startActivity(requireContext(), intent, null)
                }
            }
        }

        observeMySettings()
        observeErrors()
        observeLoading()
    }

    private fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.phoneShimmer.startShimmer()
                binding.emailShimmer.startShimmer()
                binding.profileCardShimmer.root.visibility = View.VISIBLE
                binding.profileCard.root.visibility = View.INVISIBLE

            } else {
                binding.phoneShimmer.hideShimmer()
                binding.emailShimmer.hideShimmer()
                binding.profileCard.root.visibility = View.VISIBLE
                binding.profileCardShimmer.root.visibility = View.GONE
            }
        }
    }

    private fun observeErrors() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observeMySettings() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.profileCard.userName.text = requireContext().getString(
                R.string.user_name,
                it.nickName,
            )
            binding.profileCard.userLogin.text = it.loginName
        }
    }
}