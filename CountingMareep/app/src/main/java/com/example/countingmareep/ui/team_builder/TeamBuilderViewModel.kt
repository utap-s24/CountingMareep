package com.example.countingmareep.ui.team_builder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamBuilderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is team builder Fragment"
    }
    val text: LiveData<String> = _text
}