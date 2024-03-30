package com.mezhendosina.sgo.app.ui.loginFlow.webViewLogin

import androidx.lifecycle.viewModelScope
import com.mezhendosina.sgo.app.utils.BaseViewModel
import com.mezhendosina.sgo.data.netschoolEsia.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
) : BaseViewModel() {

    fun login(code: Int) {
        viewModelScope.launch {
        }
    }
}