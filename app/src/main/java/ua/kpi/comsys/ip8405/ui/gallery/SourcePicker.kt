package ua.kpi.comsys.ip8405.ui.gallery

import android.content.Context
import com.github.michaelbull.result.*
import kotlinx.serialization.ExperimentalSerializationApi

internal class SourcePicker(private val db: ImagesDatabaseSource, private val api: ImageDataSource,) {
    @ExperimentalSerializationApi
    suspend fun getImages(request: String, count: Int) : Result<ImageGallery, Exception> {
        return try {
            val res = api.getImages(request, count)
            if (res is Ok) {
                res.value.hits.forEach { image ->
                    cacheImage(image)
                }
                return res
            }
            db.getImages().andThen {
                return if (it.hits.size < count) Err(Exception("No images found"))
                else Ok(it)
            }
        } catch (e: Throwable) {
            Err(Exception(e.message))
        }
    }

    private suspend fun cacheImage(image: Image) {
        db.getImages().onSuccess {
            val picture = it.hits.find { picture ->
                picture.largeImageURL == image.largeImageURL
            }
            if (picture == null) {
                db.addImage(image).mapError { error ->
                    println("Could not cache image\nReason: $error")
                }
            }
        }.onFailure { error ->
            println("Could not cache image\nReason: $error")
        }
    }
}

internal fun initializePicker(apiKey: String, context: Context) : SourcePicker {
    return SourcePicker(ImagesDatabaseSource(ImagesDatabase.getInstance(context).getImageDao()), ImageDataSource(apiKey))
}
