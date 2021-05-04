package ua.kpi.comsys.ip8405.ui.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ua.kpi.comsys.ip8405.R

class AddBookFragment: Fragment(R.layout.add_book_fragment) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.add_book_fragment, container, false) as ScrollView
        val addBookButton = root.findViewById<Button>(R.id.add_book_button)
        addBookButton.setOnClickListener {
            val titleTextHolder = root.findViewById<AppCompatEditText>(R.id.new_book_title)
            val title = titleTextHolder.text.toString()
            if (title.isEmpty()) {
                titleTextHolder.error = "Provide book title"
                return@setOnClickListener
            }
            val priceTextHolder = root.findViewById<EditText>(R.id.new_book_price)
            val price = priceTextHolder.text.toString()
            val regEx = Regex("[0-9]+\\.[0-9][0-9]")
            if (!price.matches(regEx)) {
                priceTextHolder.error = "Wrong value"
                return@setOnClickListener
            }
            val subtitleTextHolder = root.findViewById<AppCompatEditText>(R.id.new_book_subtitle)
            val subtitle = subtitleTextHolder.text.toString()
            if (subtitle.isEmpty()) {
                subtitleTextHolder.error = "Provide book subtitle"
                return@setOnClickListener
            }
            val book = Book(title, subtitle, "noid", "$$price", "", "", "", "", "", "", "")
            model.bookAdapter.value?.addBook(book)
            parentFragmentManager.popBackStack()
        }
        setReturnButtonListener(root)
        return root
    }

    private fun setReturnButtonListener(root : ScrollView) {
        val backButton = root.findViewById<ImageButton>(R.id.return_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}