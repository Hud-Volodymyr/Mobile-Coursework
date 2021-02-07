# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №1.1<br />
з дисципліни<br />
“Розроблення клієнтських додатків для мобільних платформ”<br />
</p>
<p align="right">
Виконав:<br />
студент групи ІП-84<br />
ЗК ІП-8405<br />
Гудь Володимир<br />
</p>

----------------------------------------------------------------------------------------------------------------

<p align="center">
<b>Приклад роботи додатка<b><br />
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/main/images/1.1_example.jpg?raw=true"/>
</p>
  
----------------------------------------------------------------------------------------------------------------
  
Приклад коду головної Activity
  
```kotlin
package com.example.coursework8405

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
```

-------------------------------------------------------------------------------------------------------------------
<b>Висновок</b> <br />
В даній лабораторній роботі ми познайомилися з Android Studio та створили перший проектз використанням навігаційної моделі вкладок.
