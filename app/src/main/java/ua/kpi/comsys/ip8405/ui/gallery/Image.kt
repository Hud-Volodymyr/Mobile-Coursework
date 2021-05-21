package ua.kpi.comsys.ip8405.ui.gallery

import kotlinx.serialization.Serializable

@Serializable
data class Image(
        val largeImageURL: String
)

@Serializable
data class ImageGallery(
        val hits: List<Image>
)
