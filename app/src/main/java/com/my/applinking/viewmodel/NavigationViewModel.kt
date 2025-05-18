package com.my.applinking.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(): ViewModel() {

    private val _deepLinkId = MutableStateFlow<String?>(null)
    val deepLinkId: StateFlow<String?> get() = _deepLinkId

    suspend fun handleIntentData(uri: Uri?) {
        uri?.lastPathSegment.let {
            if(it.isNullOrBlank().not()){
            _deepLinkId.emit(it)
            }
        }
    }

    fun clearDeeplinkId(){
        _deepLinkId.value = null
    }
}