package ua.kpi.comsys.ip8405.ui.bookList

import android.content.Context
import com.github.michaelbull.result.*
import kotlinx.serialization.ExperimentalSerializationApi

internal class SourcePicker(private val db: BooksDatabaseSource, private val api: BooksDataSource,) {
    @ExperimentalSerializationApi
    suspend fun getBooks(query: String) : Result<List<Book>, Exception> {
        val res = api.getBooks(query)
        if (res is Ok) {
            res.value.forEach { book ->
                db.getBook(book.isbn13)
                    .onFailure {
                    db.addBook(book).mapError {
                        println("Adding book ${book.title} to database failed!\n Exception: $it")
                    }
                }
            }
            return res
        }
        return db.getBooks(query)
    }

    @ExperimentalSerializationApi
    suspend fun getBook(isbn13: String) : Result<Book, Exception> {
        val res = api.getBook(isbn13)
        if (res is Ok) {
            val book = res.value
            db.getBook(book.isbn13).onSuccess {
                db.updateBook(book)
            }.onFailure {
                db.addBook(book)
            }.mapError {
                println("Updating book ${book.title} failed!\n Exception: ${it.message}")
            }
        }
        return db.getBook(isbn13)
    }
}

internal fun initializePicker(context: Context) : SourcePicker {
    return SourcePicker(BooksDatabaseSource(BooksDatabase.getInstance(context).getBookDao()), BooksDataSource())
}
