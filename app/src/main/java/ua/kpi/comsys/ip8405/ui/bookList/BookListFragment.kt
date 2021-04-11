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

}