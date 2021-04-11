# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №3<br />
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

----------------------------------------------------------------------------------------------------------------

<p align="center">
<b>Приклад роботи додатка<b><br />
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab3/images/main_vertical.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab3/images/books_vertical.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab3/images/boos_vertical_endOfList.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab3/images/books_horizontal.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab3/images/books_horizontal_endOfList.jpg?raw=true"/>
</p>
  
----------------------------------------------------------------------------------------------------------------

<p>
<b>Приклад коду для створення списку книг<b><br />
</p>
  
``` kotlin
package ua.kpi.comsys.ip8405.ui.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8405.R

class BookListFragment : Fragment() {

    private lateinit var viewModel: BookListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)
        activity?.let { viewModel = ViewModelProvider(it).get(BookListViewModel::class.java) }
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        val bookList = viewModel.filename.value?.let { BooksDataSource(it, requireActivity().assets) }
        val adapter = BookAdapter(bookList!!)
        recyclerView?.adapter = adapter
    }
```

<p>
<b>Висновок<b><br />
</p>

В даній лабораторній роботі ми навчилися створювати списки, працювати з зображення, з даними, представленими у форматі JSON на платформі андроїд за допомогою мови kotlin.
