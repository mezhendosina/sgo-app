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

package com.mezhendosina.sgo.app.ui.settingsFlow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.ContainerSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsContainer : Fragment(R.layout.container_settings) {

    lateinit var binding: ContainerSettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ContainerSettingsBinding.bind(view)

        val navHost =
            childFragmentManager.findFragmentById(R.id.settings_fragment_container) as NavHost
        val navController = navHost.navController

        val appBarConfiguration = AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener {
                this.activity?.onBackPressedDispatcher?.onBackPressed()
                true
            }.build()

        NavigationUI.setupWithNavController(
            binding.settingsToolbar,
            navController,
            appBarConfiguration
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                if (destination.id == R.id.settingsFragment) {
                    binding.appBarLayout.setExpanded(true)
                    delay(100)
                } else {
                    binding.appBarLayout.setExpanded(false)
                    delay(200)
                }
                binding.collapsingtoolbarlayout.title = destination.label
            }
        }
    }
}