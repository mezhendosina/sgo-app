package com.mezhendosina.sgo.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUiSaveStateControl
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.MainContainerBinding
import com.mezhendosina.sgo.app.findTopNavController
import com.mezhendosina.sgo.app.ui.showAnimation
import com.mezhendosina.sgo.data.checkUpdates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ContainerFragment : Fragment(R.layout.main_container) {

    private lateinit var binding: MainContainerBinding

    private val file: File = File.createTempFile("app", "apk")
    private val downloadState = MutableLiveData(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        CoroutineScope(Dispatchers.IO).launch {
            checkUpdates(requireContext(), file, downloadState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainContainerBinding.bind(view)
        val navHost = childFragmentManager.findFragmentById(R.id.tabs_container) as NavHostFragment
        val navController = navHost.navController

        binding.bottomNavigation.setupWithNavController(navController)
        NavigationUI.setupWithNavController(
            binding.toolbar,
            navController,
            AppBarConfiguration(setOf(R.id.mainFragment, R.id.journalFragment))
        )


        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    findTopNavController().navigate(R.id.action_containerFragment_to_settingsContainer)
                    true
                }
                else -> false
            }
        }
        downloadState.observe(viewLifecycleOwner) {
            val updateProgress = binding.updateProgress.root

            when (it) {
                100, 0 -> {
                    updateProgress.visibility = View.GONE
                }
                1 -> {
                    showAnimation(updateProgress)
                }
                else -> {
                    binding.updateProgress.updateProgress.setProgressCompat(it, true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        file.delete()
    }
}