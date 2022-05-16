package com.mezhendosina.sgo.app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialSharedAxis
import com.mezhendosina.sgo.app.databinding.SettingsFragmentBinding
import com.mezhendosina.sgo.data.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        viewModel.getCurrentTheme(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)

        viewModel.currentTheme.observe(viewLifecycleOwner) {
            binding.changeThemeRadioGroup.check(it)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout(requireContext())
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.changeThemeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            CoroutineScope(Dispatchers.IO).launch {
                Settings(inflater.context).setTheme(checkedId)
                withContext(Dispatchers.Main) {
                }
            }
        }
//        viewModel.currentTheme.observe(viewLifecycleOwner) {
//            binding.currentTheme.text = when (it) {
//                0 -> "Светлая"
//                1 -> "Темная"
//                2 -> "Как в системе"
//                else -> ""
//            }
//        }

//        binding.selectThemeCard.setOnClickListener {
//            changeThemeAlertDialog(
//                requireContext(),
//                arrayOf("Светлая", "Темная", "Как в системе"),
//                viewModel.currentTheme.value ?: 2
//            ) {
//                viewModel.changeTheme(requireContext(), it)
//            }
//        }

        return binding.root
    }

//    fun setTheme(theme: Resources.Theme) {
//
//
//        val w = binding.root.measuredWidth
//        val h = binding.root.measuredHeight
//
//        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        binding.root.draw(canvas)
//
//
//
//
//        val finalRadius = hypot(w.toFloat(), h.toFloat())
//        val anim = ViewAnimationUtils.createCircularReveal(container, w / 2, h / 2, 0f, finalRadius)
//        anim.duration = 400L
//        anim.doOnEnd {
//            imageView.setImageDrawable(null)
//            imageView.isVisible = false
//        }
//        anim.start()
//    }
}
