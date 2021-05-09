package ua.kpi.comsys.ip8405.ui.bookList

import retrofit2.http.GET
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import retrofit2.http.Path

internal interface BookApi {
    @GET("1.0/search/{search}")
    suspend fun getBooks(@Path("search") request: String): BooksResponse

    @GET("1.0/books/{isbn13}")
    suspend fun getBook(@Path("isbn13") isbn13: String): BookResponse
}

@ExperimentalSerializationApi
inline fun <reified T> getApi(): T {

    val json = Json {
        ignoreUnknownKeys = true
    }.asConverterFactory(MediaType.parse("application/json")!!)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.itbook.store/")
        .addConverterFactory(json)
        .build()

    return retrofit.create(T::class.java)
}