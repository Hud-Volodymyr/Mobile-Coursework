package ua.kpi.comsys.ip8405.ui.bookList

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Book(
    @SerialName("title")
    val title: String,

    @SerialName("subtitle")
    val subtitle: String,

    val isbn13: String,

    @SerialName("price")
    val price: String,

    @SerialName("year")
    val year: String = "Unknown year",

    @SerialName("image")
    val image: String,

    @SerialName("desc")
    val desc: String = "No description provided",

    @SerialName("authors")
    val authors: String = "No information about authors",

    @SerialName("pages")
    val pages: String = "No information about page count",

    @SerialName("rating")
    val rating: String = "No ratings available",

    @SerialName("publisher")
    val publisher: String = "No publisher info"
)