# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №1.2<br />
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
  Варіант = 05 mod 2 + 1 = 2 
</p>

<p>
<b>Приклад коду для завдання 1<b><br />
</p>
  
``` kotlin
package com.example.coursework8405
import java.util.*
import kotlin.math.ceil

fun main() {
    val studentsStr = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; Лихацька Юлія- ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82"

    val studentsGroups = studentsStr
            .split("; ")
            .map { it.split("- ") }
            .groupBy { it.last() }
            .mapValues { (key, value) -> value.map { it.first() } }

    println("Завдання 1")
    println(studentsGroups);

    val points = listOf(12, 12, 12, 12, 12, 12, 12, 16)
    val studentsPoints = studentsGroups
            .mapValues { (key, value) ->
               value.map {
                   it to List(points.size) { i -> randomValue(points[i])}
               }.groupBy {
                   it.first
               }.mapValues { (key, el) ->
                   el.first().second
               }
            }

    println("Завдання 2")
    println(studentsPoints)

    val sumPoints = studentsPoints
            .mapValues { (key, value) ->
                value.mapValues { (k,v)  ->
                    v.sum()
                }
            }

    println("Завдання 3")
    println(sumPoints)

    val groupAvg = sumPoints.mapValues { (key, value) ->
        value.values.sum() / (value.size * points.size)
    }
    println("Завдання 4")
    println(groupAvg)

    val passedPerGroup = sumPoints.mapValues { (key, value) ->
        value.filterValues { it >= 60 }.keys
    }
    println("Завдання 5")
    println(passedPerGroup)
}

fun randomValue(maxValue: Int): Int {
    val random = Random()

    return when (random.nextInt(6)) {
        1 -> ceil(maxValue * 0.7).toInt()
        2 -> ceil(maxValue * 0.9).toInt()
        3, 4, 5 -> maxValue
        else -> 0
    }
}

main();
```

<p>
<b>Висновок<b><br />
</p>

В даній лабораторній роботі ми познайомилися з основами програмування на мові kotlin.
