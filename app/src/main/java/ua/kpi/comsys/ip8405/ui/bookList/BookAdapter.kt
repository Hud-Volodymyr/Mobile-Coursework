package ua.kpi.comsys.ip8405.ui.bookList

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8405.R

internal class BookAdapter(private val datasource: BooksDataSource): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private val books = datasource.getBooks()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        val image = datasource.getImage(books[position])
        holder.bind(books[position], image)
    }

    inner class BookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.bookTitle)
        private val subtitle = view.findViewById<TextView>(R.id.bookSubtitle)
        private val price = view.findViewById<TextView>(R.id.bookPrice)
        private val isbn13 = view.findViewById<TextView>(R.id.bookIsbn13)
        private val image = view.findViewById<ImageView>(R.id.bookImage)

        fun bind(book: Book, drawable: Drawable? = null) {
            title.text = book.title
            subtitle.text = book.subtitle
            price.text = book.price
            isbn13.text = book.isbn13
            image.setImageDrawable(
                    drawable ?: ContextCompat.getDrawable(itemView.context, R.drawable.ic_nocover)
            )
        }
    }

    override fun getItemCount(): Int = books.size
}