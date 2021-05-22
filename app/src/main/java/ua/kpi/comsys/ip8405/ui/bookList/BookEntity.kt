package ua.kpi.comsys.ip8405.ui.bookList

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "books", indices = [Index(value = ["isbn13"], unique = true)])
internal data class BookEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    val title: String,

    val subtitle: String,

    val isbn13: String,

    val price: String,

    val year: String = "Unknown year",

    val image: String,

    val desc: String = "No description provided",

    val authors: String = "No information about authors",

    val pages: String = "No information about page count",

    val rating: String = "No ratings available",

    val publisher: String = "No publisher info",

    val url: String = "No url"
) {
    internal fun convertToBook() : Book {
        return Book(
            title,
            subtitle,
            isbn13,
            price,
            year,
            image,
            desc,
            authors,
            pages,
            rating,
            publisher,
            url
        )
    }
}