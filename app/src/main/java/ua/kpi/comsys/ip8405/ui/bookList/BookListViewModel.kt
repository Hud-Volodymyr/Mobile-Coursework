package ua.kpi.comsys.ip8405.ui.bookList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookListViewModel : ViewModel() {
    private val _datasource = MutableLiveData<String>().apply {
        value = "BooksList.txt"
    }
    val filename: LiveData<String> = _datasource
}