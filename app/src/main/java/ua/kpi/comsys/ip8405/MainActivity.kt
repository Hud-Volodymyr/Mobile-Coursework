package ua.kpi.comsys.ip8405

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MainActivity : AppCompatActivity() {
    private val resultListeners = mutableMapOf<Int, (Int, Intent?) -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var onBackPressedListener: (() -> Unit)? = null

    override fun onBackPressed() {
        onBackPressedListener?.invoke() ?: finish()
    }

    fun startActivityForResult(intent: Intent, requestCode: Int, cb: (Int, Intent?) -> Unit) {
        resultListeners[requestCode] = cb
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        resultListeners[requestCode]?.invoke(resultCode, data)
    }
}