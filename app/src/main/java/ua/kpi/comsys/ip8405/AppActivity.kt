package ua.kpi.comsys.ip8405

import android.os.Bundle
import ua.kpi.comsys.ip8405.ui.navigation.NavFragment

class AppActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, NavFragment())
        }.commit()
    }
}