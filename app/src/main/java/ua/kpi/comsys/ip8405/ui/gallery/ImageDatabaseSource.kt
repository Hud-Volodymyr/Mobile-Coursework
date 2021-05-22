package ua.kpi.comsys.ip8405.ui.gallery

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok

internal class ImagesDatabaseSource(private val imageDao: ImageDataAccessObject) {

    suspend fun getImages(): Result<ImageGallery, Exception> {
        return try {
            Ok(imageDao.getImages().convertToImagesGallery())
        } catch (e: Throwable) {
            Err(Exception(e.message))
        }
    }

    suspend fun addImage(image: Image): Result<Unit, Exception> {
        return try {
            val entity = ImageEntity(0, image.largeImageURL)
            Ok(imageDao.addImages(entity))
        } catch (e: Throwable) {
            Err(Exception(e.message))
        }
    }
}