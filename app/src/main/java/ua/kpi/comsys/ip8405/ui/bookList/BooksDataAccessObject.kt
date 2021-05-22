package ua.kpi.comsys.ip8405.ui.bookList

import androidx.room.*


@Dao
internal interface BooksDataAccessObject {
    @Insert
    suspend fun insert(vararg books: BookEntity)

    @Update
    suspend fun update(vararg book: BookEntity)

    @Query("SELECT * FROM books")
    suspend fun selectAll() : List<BookEntity>

    @Query("SELECT * FROM books WHERE isbn13 = :isbn13")
    suspend fun selectById(isbn13: String) : BookEntity
}