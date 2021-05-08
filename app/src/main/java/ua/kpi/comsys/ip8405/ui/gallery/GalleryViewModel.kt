package ua.kpi.comsys.ip8405.ui.gallery

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    internal val galleryAdapter = MutableLiveData<GalleryAdapter?>()

    internal fun setGalleryAdapter(newAdapter: GalleryAdapter?) {
        galleryAdapter.value = newAdapter
    }
}