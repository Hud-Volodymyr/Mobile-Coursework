package ua.kpi.comsys.ip8405.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.github.michaelbull.result.Result
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class GalleryViewModel : ViewModel() {
    internal val galleryAdapter = MutableLiveData<GalleryAdapter?>()
    private val dataSource = ImageDataSource("19193969-87191e5db266905fe8936d565")
    private val request = "red+cars"
    private val count = 21
    val picasso = Picasso.get()
    val state = MutableLiveData(false)
    val imageList = MutableLiveData<Result<ImageGallery, Exception>>()

    internal fun setGalleryAdapter(newAdapter: GalleryAdapter?) {
        galleryAdapter.value = newAdapter
    }

    @ExperimentalSerializationApi
    fun provideImages() {
        if (state.value == true) return

        state.value = true

        viewModelScope.launch {
            imageList.postValue(dataSource.getImages(request, count))
            state.postValue(false)
        }
    }
}