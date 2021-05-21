package ua.kpi.comsys.ip8405.ui.bookList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ua.kpi.comsys.ip8405.R

internal class BookAdapter(private var books: List<Book> = listOf()): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var onBookClickListener: ((Book) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        val book = books[position]
        val image = books[position].image
        holder.bind(book, image) { onBookClickListener?.invoke(book) }
    }

    inner class BookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.bookTitle)
        private val subtitle = view.findViewById<TextView>(R.id.bookSubtitle)
        private val price = view.findViewById<TextView>(R.id.bookPrice)
        private val isbn13 = view.findViewById<TextView>(R.id.bookIsbn13)
        private val image = view.findViewById<ImageView>(R.id.bookImage)

        fun bind(book: Book, cover: String? = null, onClick: () -> Unit) {
            title.text = book.title
            subtitle.text = book.subtitle
            price.text = book.price
            isbn13.text = book.isbn13
            if (image != null) {
                Picasso.get().load(cover).placeholder(R.drawable.ic_nocover).error(R.drawable.ic_nocover).into(image)
            } else {
                val noCover = ContextCompat.getDrawable(itemView.context, R.drawable.ic_nocover)
                image?.setImageDrawable(noCover)
            }
            itemView.setOnClickListener { onClick() }
        }
    }

    fun setOnBookClickListener(callback: (Book) -> Unit) {
        onBookClickListener = callback
    }

    override fun getItemCount(): Int = books.size

    fun update(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}