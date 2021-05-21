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
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AdditionalInfoFragment : Fragment(R.layout.additional_info_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.additional_info_fragment, container, false) as ConstraintLayout
    }

    @SuppressLint("SetTextI18n")
    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookString = arguments?.getString(BOOK_BUNDLE) ?: error("No Book")
        val book = Json.decodeFromString<Book>(bookString)
        val layout = getView() as ConstraintLayout
        layout.findViewById<TextView>(R.id.title_additional).text = "Title: ${book.title}"
        layout.findViewById<TextView>(R.id.subtitle_additional).text = "Subtitle: ${book.subtitle}"
        layout.findViewById<TextView>(R.id.description_additional).text = "Description: ${book.desc}"
        layout.findViewById<TextView>(R.id.authors_additional).text = "Authors: ${book.authors}"
        layout.findViewById<TextView>(R.id.publisher_additional).text = "Publisher: ${book.publisher}"
        layout.findViewById<TextView>(R.id.pages_additional).text = "Pages: ${book.pages}"
        layout.findViewById<TextView>(R.id.year_additional).text = "Year: ${book.year}"
        layout.findViewById<TextView>(R.id.rating_additional).text = "Rating: ${book.rating} / 5"
        Picasso.get()
            .load(book.image)
            .placeholder(R.drawable.ic_nocover)
            .error(R.drawable.ic_nocover)
            .into(layout.findViewById<ImageView>(R.id.image_additional))
        setReturnButtonListener(layout)
    }

    @SuppressLint("SetTextI18n")
    private fun setBookProperties(root : ConstraintLayout, book: Book) {
    }

    private fun setReturnButtonListener(root : ConstraintLayout) {
        val backButton = root.findViewById<ImageButton>(R.id.return_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val BOOK_BUNDLE = "BOOK_BUNDLE"
    }
}