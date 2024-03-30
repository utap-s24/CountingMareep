package com.example.countingmareep.ui.box

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BoxViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is box Fragment"
    }
    val text: LiveData<String> = _text
}