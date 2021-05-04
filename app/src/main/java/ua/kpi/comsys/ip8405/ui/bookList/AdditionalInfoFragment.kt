package ua.kpi.comsys.ip8405.ui.bookList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.kpi.comsys.ip8405.R
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class AdditionalInfoFragment : Fragment(R.layout.additional_info_fragment) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.additional_info_fragment, container, false) as ConstraintLayout
        val bookId = model.isbn13.value
        val book = bookId?.let { model.bookAdapter.value?.ds?.getBook(it) }
        if (book != null) {
            setBookProperties(root, book)
        } else {
            parentFragmentManager.popBackStack()
        }
        setReturnButtonListener(root)
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun setBookProperties(root : ConstraintLayout, book: Book) {
        root.findViewById<TextView>(R.id.title_additional).text = "Title: ${book.title}"
        root.findViewById<TextView>(R.id.subtitle_additional).text = "Subtitle: ${book.subtitle}"
        root.findViewById<TextView>(R.id.description_additional).text = "Description: ${book.desc}"
        root.findViewById<TextView>(R.id.authors_additional).text = "Authors: ${book.authors}"
        root.findViewById<TextView>(R.id.publisher_additional).text = "Publisher: ${book.publisher}"
        root.findViewById<TextView>(R.id.pages_additional).text = "Pages: ${book.pages}"
        root.findViewById<TextView>(R.id.year_additional).text = "Year: ${book.year}"
        root.findViewById<TextView>(R.id.rating_additional).text = "Rating: ${book.rating} / 5"
        val image = model.bookAdapter.value?.ds?.getImage(book)
        root.findViewById<ImageView>(R.id.image_additional).setImageDrawable(
            image?: ContextCompat.getDrawable(root.context, R.drawable.ic_nocover)
        )
    }

    private fun setReturnButtonListener(root : ConstraintLayout) {
        val backButton = root.findViewById<ImageButton>(R.id.return_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}