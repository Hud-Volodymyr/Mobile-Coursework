package ua.kpi.comsys.ip8405.ui.gallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
internal data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val imageUrl: String,
) {
    internal fun convertToImage() : Image {
        return Image(imageUrl)
    }
}

internal fun <T: Iterable<ImageEntity>> T.convertToImages() = map(ImageEntity::convertToImage)
internal fun <T: Iterable<ImageEntity>> T.convertToImagesGallery() = ImageGallery(this.convertToImages())