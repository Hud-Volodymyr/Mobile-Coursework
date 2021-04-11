package ua.kpi.comsys.ip8405.ui.bookList

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.charset.Charset

internal class BooksDataSource(private val filename: String, private val assetManager: AssetManager) {
    fun getBooks(): ArrayList<Book> {
        val books = assetManager.open(filename).bufferedReader(Charset.defaultCharset()).readText()

        return Json.decodeFromString<BookData>(books).data
    }

    fun getImage(book: Book): Drawable? {
        return try {
            Drawable.createFromStream(assetManager.open(book.image), null)
        } catch(exception: IOException) {
            return null
        }
    }

    @Serializable
    private data class BookData(@SerialName("books") val data: ArrayList<Book>)
}