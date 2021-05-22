package ua.kpi.comsys.ip8405.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Ok
import com.squareup.picasso.Picasso
import com.github.michaelbull.result.Result
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class GalleryViewModel : ViewModel() {
    internal val galleryAdapter = MutableLiveData<GalleryAdapter?>()
    internal var dataSourcePicker : SourcePicker? = null
    private val request = "red+cars"
    private val count = 21
    val picasso = Picasso.get()
    val state = MutableLiveData(listOf(false, false))
    val imageList = MutableLiveData<Result<ImageGallery, Exception>>()

    internal fun setGalleryAdapter(newAdapter: GalleryAdapter?) {
        galleryAdapter.value = newAdapter
    }

    @ExperimentalSerializationApi
    fun provideImages() {
        if (state.value?.first() == true && state.value?.last() == true) return

        state.value = listOf(true, true)

        viewModelScope.launch {
            val image = dataSourcePicker?.getImages(request, count)!!
            if (image is Ok) {
                imageList.postValue(image)
                state.postValue(listOf(false, false))
            } else {
                state.postValue(listOf(false, true))
            }
        }
    }
}