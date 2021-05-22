package ua.kpi.comsys.ip8405.ui.bookList

import android.content.Context
import androidx.room.*

@Database(entities = [BookEntity::class], version = 1)
internal abstract class BooksDatabase : RoomDatabase() {
    abstract fun getBookDao(): BooksDataAccessObject

    companion object {
        private const val DATABASE = "books_db"
        @Volatile
        private var instance: BooksDatabase? = null

        fun getInstance(context: Context): BooksDatabase {
            instance = instance
                ?: synchronized(this) {
                    instance ?: createDatabase(context).also {
                        instance = it
                    }
            }
            return instance as BooksDatabase
        }

        private fun createDatabase(context: Context): BooksDatabase {
            return Room.databaseBuilder(
                context,
                BooksDatabase::class.java,
                DATABASE
            ).build()
        }
    }
}