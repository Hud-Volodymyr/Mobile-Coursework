package ua.kpi.comsys.ip8405.ui.bookList

import android.util.Log
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok

internal class BooksDataSource {
    @ExperimentalSerializationApi
    private val bookApi = getApi<BookApi>()

    @ExperimentalSerializationApi
    suspend fun getBooks(request: String?) : Result<List<Book>, Exception> {
        if (request == null) return Err(Exception("No books found"))
        try {
            val res = bookApi.getBooks(request)

            if (res.books == null) {
                return Err(Exception("No books found"))
            }

            return Ok(res.books)
        } catch (e: Throwable) {
            return Err(Exception(e))
        }
    }

    @ExperimentalSerializationApi
    suspend fun getBook(isbn13: String): Result<Book, Exception> {
        try {
            val res = bookApi.getBook(isbn13)

            if (res.error != "0") {
                return Err(Exception(res.error))
            }

            Log.d("BOOK_DECODE", res.toString())

            return Ok(res.bookSerialize())
        } catch (e: Throwable) {
            return Err(Exception(e.message))
        }
    }

    @Serializable
    private data class BooksData(@SerialName("books") val data: List<Book>)
}
