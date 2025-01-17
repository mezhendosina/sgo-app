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

package com.mezhendosina.sgo.app.ui.journalFlow.answer

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.mezhendosina.sgo.app.R
import com.mezhendosina.sgo.app.databinding.FragmentAnswerBinding
import com.mezhendosina.sgo.app.model.answer.FileUiEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment : Fragment(R.layout.fragment_answer) {
    private var binding: FragmentAnswerBinding? = null

    internal val viewModel by activityViewModels<AnswerViewModel>()
    private var adapter: AnswerFileAdapter? = null

//    private val selectFileLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            val path = it.data?.data
//            if (path?.path != null) {
//                val fileName = getFileNameFromUri(requireContext(), path)
//                viewModel.addFile()
//                adapter!!.files = adapter!!.files.plus(fileEntity)
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)

        adapter =
            AnswerFileAdapter(
                viewModel,
                object : FileActionListener {
                    override fun onClick(file: FileUiEntity) {
                        viewModel.openFile(requireContext(), file)
                    }

                    override fun deleteFile(attachmentId: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun editDescription(attachmentId: Int) {
                        TODO("Not yet implemented")
                    }
                },
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.attachments.invalidate()
        binding = null
        adapter = null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnswerBinding.bind(view)

        binding!!.answerEditText.setText(viewModel.getAnswer())

        hideUnusedElementsInHomework()
        setupHomework()
        setupAttachments()
        setupOnEditAnswer()
    }

//    private fun addOnAttachFileClickListener() {
//        binding!!.attachFile.setOnClickListener {
//            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            intent.type = "*/*"
//            selectFileLauncher.launch(intent)
//        }
//    }

    private fun setupAttachments() {
        binding!!.attachments.adapter = adapter
        binding!!.attachments.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupHomework() {
        val homework = viewModel.getHomework()
        binding!!.homework.homeworkBody.text = homework
    }

    private fun setupOnEditAnswer() {
        binding!!.answer.editText?.addTextChangedListener {
            viewModel.editAnswerText(it.toString())
        }
    }

    private fun hideUnusedElementsInHomework() {
        with(binding!!.homework) {
            attachmentsList.root.visibility = View.GONE
            commentBody.visibility = View.GONE
            commentHeader.visibility = View.GONE
            copyIcon.visibility = View.GONE
            addAnswerButton.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val ADD_ANSWER = "add"
        const val EDIT_ANSWER = "edit"
    }
}
