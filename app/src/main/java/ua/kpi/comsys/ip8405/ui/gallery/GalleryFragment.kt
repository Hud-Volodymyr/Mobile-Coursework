package ua.kpi.comsys.ip8405.ui.gallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8405.R

class GalleryFragment: Fragment(R.layout.gallery_fragment) {
    private lateinit var galleryViewModel: GalleryViewModel

    private fun createGalleryAdapter() {
        galleryViewModel.setGalleryAdapter(context?.let { GalleryAdapter(mutableListOf<Uri>()) })
    }

    private fun addFloatingImageActionClickListener(root: View) {
        val addImageButton = root.findViewById<ImageButton>(R.id.imageCreatorButton)
        addImageButton.setOnClickListener {
            chooseImageGallery()
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            galleryViewModel.galleryAdapter.value?.addImage(data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        galleryViewModel = ViewModelProvider(requireActivity()).get(GalleryViewModel::class.java)
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root = getView()
        val recyclerView = root?.findViewById<RecyclerView>(R.id.galleryRecyclerView)
        recyclerView?.layoutManager = GalleryLayoutManager()
        if (galleryViewModel.galleryAdapter.value == null) {
            createGalleryAdapter()
        }
        recyclerView?.adapter = galleryViewModel.galleryAdapter.value
        if (root != null) {
            addFloatingImageActionClickListener(root)
        }
    }
}