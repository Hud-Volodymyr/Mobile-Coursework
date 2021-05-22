package ua.kpi.comsys.ip8405.ui.bookList

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.util.*

internal class BooksDatabaseSource(private val booksDao: BooksDataAccessObject) {
    suspend fun addBook(book: Book): Result<Unit, Exception> {
        return query {
            booksDao.insert(book.convertToEntity())
        }
    }

    suspend fun getBook(isbn13: String) : Result<Book, Exception> {
        return query {
            booksDao.selectById(isbn13).convertToBook()
        }
    }

    suspend fun getBooks(query: String) : Result<List<Book>, Exception> {
        return query {
            booksDao.selectAll().map {

                it.convertToBook()
            }.filter{
                it.title.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
            }
        }
    }

    suspend fun updateBook(book: Book) : Result<Unit, Exception> {
        return query {
            val bookEntity = booksDao.selectById(book.isbn13)
            val updater = book.convertToEntity().copy(uid = bookEntity.uid)
            booksDao.update(updater)
        }
    }

    private suspend fun <T> query(action: suspend () -> T): Result<T, Exception> {
        return try {
            Ok(action())
        } catch (e: Throwable) {
            Err(Exception(e.message))
        }
    }
}