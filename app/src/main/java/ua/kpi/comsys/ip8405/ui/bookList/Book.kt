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

    @SerialName("image")
    val image: String
)