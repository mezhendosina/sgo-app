package com.mezhendosina.sgo.app.ui.uploadFileBottomSheet

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.BottomSheetUploadFileBinding
import com.mezhendosina.sgo.data.requests.homework.entities.File

class UploadFileBottomSheet(
    private val actionType: Int,
    private val assignmentId: Int,
    private val file: File? = null,
    private val onSuccess: () -> Unit
) :
    BottomSheetDialogFragment(R.layout.bottom_sheet_upload_file) {

    private lateinit var binding: BottomSheetUploadFileBinding
    private val viewModel: UploadFileViewModel by viewModels()

    private val filePath = MutableLiveData<ActivityResult>()

    private val selectFile =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            filePath.value = it
        }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BottomSheetUploadFileBinding.bind(view)

        if (file != null) {
            binding.description.editText?.setText(file.description)
            binding.sendFile.text = file.fileName
        }
        when (actionType) {
            EDIT_DESCRIPTION -> {
                binding.header.setText(R.string.edit_description)
                binding.selectFile.isClickable = false
                binding.sendFile.setText(R.string.edit_description)
                binding.sendFile.setOnClickListener { editDescription(binding.description.editText?.text.toString()) }
            }
            REPLACE_FILE -> {
                binding.header.setText(R.string.replace_file)
                binding.sendFile.setText(R.string.replace_file)
                binding.sendFile.setOnClickListener { replaceFile() }
            }
            else -> {
                binding.header.setText(R.string.upload_file)
                binding.sendFile.setOnClickListener { sendFile() }
            }
        }


        binding.selectFile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (requireContext().checkSelfPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission.launch(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                }
            } else {
                if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            selectFile.launch(intent)
        }

        filePath.observe(viewLifecycleOwner) {
            if (it != null) {
                val path = it.data?.data?.normalizeScheme()?.path
                binding.selectFile.text = path?.substring(path.lastIndexOf("/") + 1)
            }
        }

        observeErrors()
        observeSuccess()
    }

    private fun sendFile() {
        val file = filePath.value?.data?.data
        if (file != null) {
            viewModel.sendFile(
                assignmentId,
                file,
                binding.description.editText?.text.toString()
            )
        }
    }

    private fun editDescription(description: String) {
        viewModel.editFileDescription(file?.id ?: 0, description)
    }

    private fun replaceFile() {
        TODO()
    }

    private fun observeSuccess() {
        viewModel.success.observe(viewLifecycleOwner) {
            if (it) {
                onSuccess.invoke()
                dismiss()
            }
        }

    }

    private fun observeErrors() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val UPLOAD_FILE = 0
        const val EDIT_DESCRIPTION = 1
        const val REPLACE_FILE = 2
    }
}
