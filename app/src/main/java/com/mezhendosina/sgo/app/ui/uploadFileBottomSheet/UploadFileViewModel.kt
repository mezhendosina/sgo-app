package com.mezhendosina.sgo.app.ui.uploadFileBottomSheet

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mezhendosina.sgo.Singleton
import com.mezhendosina.sgo.app.model.homework.HomeworkSource
import com.mezhendosina.sgo.app.toDescription
import com.mezhendosina.sgo.data.requests.homework.entities.SendFileRequestEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class UploadFileViewModel(
    private val homeworkSource: HomeworkSource = Singleton.homeworkSource
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun sendFile(assignmentID: Int, filePath: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(filePath)
                homeworkSource.sendFileAttachment(
                    file,
                    SendFileRequestEntity(
                        true,
                        assignmentID,
                        description,
                        file.name
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.toDescription()
                }
            }
        }
    }
}