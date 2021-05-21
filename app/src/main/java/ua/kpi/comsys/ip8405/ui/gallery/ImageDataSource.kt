package ua.kpi.comsys.ip8405.ui.gallery

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.ExperimentalSerializationApi

class ImageDataSource(apiKey: String) {
    @ExperimentalSerializationApi
    private val imageApi: ImagesApi = getImageApi(apiKey)

    @ExperimentalSerializationApi
    suspend fun getImages(request: String, count: Int) : Result<ImageGallery, Exception> {
        return try {
            Ok(imageApi.getImages(request, count.toString()))
        } catch (error: Throwable) {
            Err(Exception(error.message))
        }
    }
}