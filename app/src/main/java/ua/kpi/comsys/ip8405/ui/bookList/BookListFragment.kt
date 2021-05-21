    package ua.kpi.comsys.ip8405.ui.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.*
import ua.kpi.comsys.ip8405.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedViewModel : ViewModel() {
    internal val bookAdapter = MutableLiveData<BookAdapter?>()
    val isbn13 = MutableLiveData<String>()
    val parentFragmentManager = MutableLiveData<FragmentManager?>()
    private val datasource = BooksDataSource()
    val state = MutableLiveData(false)
    internal val book = MutableLiveData<Result<Book, Exception>>()
    internal val books = MutableLiveData<Result<List<Book>, Exception>>()
    @ExperimentalSerializationApi
    fun provideBooks(request: String?) {
        state.value = true
        if (request.isNullOrBlank()) {
            books.postValue(Err(Exception("Please enter at least 3 symbols to start the search")))
            state.value = false
            return
        }

        if (request.length < 3) {
            books.postValue(Err(Exception("Please enter at least 3 symbols to start the search")))
            state.value = false
            return
        }

        if (request.contains(Regex("[^A-Za-z0-9 -'`]"))) {
            books.postValue(Err(Exception("Illegal characters")))
            state.value = false
            return
        }

        viewModelScope.launch {
            books.postValue(datasource.getBooks(request))
            state.value = false
        }
    }

    @ExperimentalSerializationApi
    fun provideBook(isbn13: String) {
        if (state.value == true) return
        state.postValue(true)

        viewModelScope.launch {
            book.postValue(datasource.getBook(isbn13))
            state.postValue(false)
        }
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        parentFragmentManager.value = fragmentManager
    }
}


class BookListFragment : Fragment(R.layout.book_list_fragment) {
    private lateinit var model: SharedViewModel
    private lateinit var noItemsFound: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        if (model.parentFragmentManager.value == null) model.setFragmentManager(parentFragmentManager)
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        noItemsFound = view.findViewById(R.id.not_found)!!
        progressBar = view.findViewById(R.id.progress_bar)!!
        if (model.bookAdapter.value == null) {
            model.bookAdapter.value = BookAdapter().apply {
                setOnBookClickListener {
                    model.book.value = null
                    model.book.observe(viewLifecycleOwner, Observer@{ res ->
                        if (res == null) return@Observer
                        model.book.removeObservers(viewLifecycleOwner)
                        res.onSuccess {
                            val fragment = AdditionalInfoFragment().apply {
                                val json = Json.encodeToString(it)
                                arguments = bundleOf(AdditionalInfoFragment.BOOK_BUNDLE to json)
                            }
                            activity?.supportFragmentManager?.commit {
                                replace(R.id.container, fragment)
                                addToBackStack(fragment::class.simpleName)
                            }
                        }.onFailure { error ->
                            Toast.makeText(context, error.message?: "Unknown error during retrieving book additional info", Toast.LENGTH_SHORT).show()
                        }
                    })
                    model.provideBook(it.isbn13)
                }
            }
        }
        recyclerView?.adapter = model.bookAdapter.value
        addSearchListener(view)
        progressBar.isIndeterminate = true
        model.state.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }
        model.books.observe(viewLifecycleOwner) {
            it.onSuccess { books ->
                recyclerView?.isVisible = true
                noItemsFound.isVisible = false
                model.bookAdapter.value!!.update(books)
            }
            it.onFailure { error ->
                recyclerView?.isVisible = false
                noItemsFound.isVisible = true
                noItemsFound.text = error.message
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
                model.provideBooks(newText)
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }
}