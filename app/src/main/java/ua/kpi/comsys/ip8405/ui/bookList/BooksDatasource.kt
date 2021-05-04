package ua.kpi.comsys.ip8405.ui.bookList

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.charset.Charset

internal class BooksDataSource(private val assetManager: AssetManager, private val filename: String = BOOKS_LOCATION) {
    fun getBooks(): ArrayList<Book> {
        val books = assetManager.open(filename).bufferedReader(Charset.defaultCharset()).readText()

        return Json.decodeFromString<BooksData>(books).data
    }

    fun getImage(book: Book): Drawable? {
        return try {
            Drawable.createFromStream(assetManager.open(book.image), null)
        } catch(exception: IOException) {
            return null
        }
    }

    fun getBook(location: String): Book?  = try {
        val book = assetManager.open("$location.txt").bufferedReader(Charset.defaultCharset()).readText()

        Json.decodeFromString(book)
    } catch (err: IOException) {
        null
    }

    @Serializable
    private data class BooksData(@SerialName("books") val data: ArrayList<Book>)

    companion object {
        private const val BOOKS_LOCATION = "BooksList.txt"
    }
}