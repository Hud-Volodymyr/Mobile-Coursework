package ua.kpi.comsys.ip8405.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavViewModel : ViewModel() {
    val state = MutableLiveData(State.Home)

    enum class State {
        Home, Charts, Books, Gallery
    }
}
