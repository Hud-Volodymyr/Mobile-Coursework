# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №4<br />
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
  Варіант = 05 mod 6 + 1 = 6 
</p>

----------------------------------------------------------------------------------------------------------------

<p align="center">
<b>Приклад роботи додатка<b><br />
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/boos_vertical_endOfList.jpgraw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_vertical.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_not_found.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_info_vertical.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_info_horizontal.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_horizontal_endOfList.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_horizontal.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_create_vertical.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/books_create_horizontal.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab4/images/book_search.jpg?raw=true"/>
</p>
  
----------------------------------------------------------------------------------------------------------------

<p>
<b>Приклад коду списку книг<b><br />
</p>
  
``` kotlin
package ua.kpi.comsys.ip8405.ui.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.kpi.comsys.ip8405.R
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SharedViewModel : ViewModel() {
    internal val bookAdapter = MutableLiveData<BookAdapter?>()
    val isbn13 = MutableLiveData<String>()
    val parentFragmentManager = MutableLiveData<FragmentManager?>()

    internal fun setBookAdapter(newAdapter: BookAdapter?) {
        bookAdapter.value = newAdapter
    }

    fun onBookClicked(newIsbn13 : String) {
        isbn13.value = newIsbn13
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        parentFragmentManager.value = fragmentManager
    }
}


class BookListFragment : Fragment(R.layout.book_list_fragment) {
    private val model: SharedViewModel by activityViewModels()
    private lateinit var noItemsFound: TextView
    internal var booksList : ArrayList<Book>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        retainInstance = true
        if (model.parentFragmentManager.value == null) model.setFragmentManager(parentFragmentManager)
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        if (model.bookAdapter.value == null) {
            val bookList = BooksDataSource(requireActivity().assets)
            val clickListener = BookAdapter.BookClickListener { isbn13: String ->
                activity?.supportFragmentManager?.commit {
                    model.onBookClicked(isbn13)
                    replace(R.id.nav_host_fragment, AdditionalInfoFragment())
                    addToBackStack(null)
                } }
            model.setBookAdapter(context?.let { BookAdapter(bookList, clickListener) })
        }
        recyclerView?.adapter = model.bookAdapter.value
        booksList = model.bookAdapter.value?.books
        noItemsFound = view.findViewById(R.id.not_found)!!
        noItemsFound.isVisible = false
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView?.adapter as BookAdapter
                val position = viewHolder.adapterPosition
                val item = adapter.books[position]
                adapter.removeBook(position)
                booksList?.remove(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        addFloatingBookActionClickListener(view)
        addSearchListener(view)
    }

    private fun addFloatingBookActionClickListener(root: View) {
        val addBookButton = root.findViewById<ImageButton>(R.id.book_creator_button)
        addBookButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.nav_host_fragment, AddBookFragment())
                addToBackStack(null)
            }
        }
    }

    private fun addSearchListener(root: View) {
        val searchView = root.findViewById<SearchView>(R.id.searchBook)
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (booksList?.isEmpty() == true) return true
                if (newText != null) {
                    val filteredBookList = booksList?.filter { book -> book.title.toLowerCase().contains(newText.toLowerCase()) } as ArrayList<Book>
                    model.bookAdapter.value?.books = filteredBookList
                    model.bookAdapter.value?.notifyDataSetChanged()
                    noItemsFound.isVisible = filteredBookList.size == 0
                }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }
}
```

<p>
<b>Висновок<b><br />
</p>

В даній лабораторній роботі ми навчилися додавати функціонал до існуючого, а також працювати з створенням, видаленням та пошуком сутностей на платформі Android.
