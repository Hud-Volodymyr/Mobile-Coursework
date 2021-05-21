package ua.kpi.comsys.ip8405.ui.gallery

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("/api/")
    suspend fun getImages(@Query("q") query: String, @Query("per_page") count: String, @Query("image_type") type: String = "photo") : ImageGallery
}

@ExperimentalSerializationApi
inline fun <reified T> getImageApi(apiKey: String): T {
    val client = OkHttpClient.Builder()
            .addInterceptor(ImagesInterceptor(apiKey))
            .build()

    val json = Json {
        ignoreUnknownKeys = true
    }.asConverterFactory(MediaType.parse("application/json")!!)

    val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .client(client)
            .addConverterFactory(json)
            .build()

    return retrofit.create(T::class.java)
}

