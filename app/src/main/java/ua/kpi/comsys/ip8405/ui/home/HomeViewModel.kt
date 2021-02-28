package ua.kpi.comsys.ip8405.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Гудь Володимир\nІП-84\nЗК ІП8405"
    }
    val text: LiveData<String> = _text
}