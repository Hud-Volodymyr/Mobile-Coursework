package ua.kpi.comsys.ip8405.ui.gallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.serialization.ExperimentalSerializationApi
import ua.kpi.comsys.ip8405.R

class GalleryFragment: Fragment(R.layout.gallery_fragment) {
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var noItemsFound: TextView

    private fun createGalleryAdapter() {
        galleryViewModel.setGalleryAdapter(context?.let { GalleryAdapter(handler = galleryViewModel.picasso) })
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
            galleryViewModel.galleryAdapter.value?.addImage(Image(data?.data.toString()))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        galleryViewModel = ViewModelProvider(requireActivity()).get(GalleryViewModel::class.java)
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root = getView()
        val recyclerView = root?.findViewById<RecyclerView>(R.id.galleryRecyclerView)
        progressBar = view.findViewById(R.id.progress_bar)!!
        noItemsFound = view.findViewById(R.id.not_found)!!
        recyclerView?.layoutManager = GalleryLayoutManager()
        if (galleryViewModel.galleryAdapter.value == null) {
            createGalleryAdapter()
        }
        recyclerView?.adapter = galleryViewModel.galleryAdapter.value
        if (root != null) {
            addFloatingImageActionClickListener(root)
        }

        progressBar.isIndeterminate = true
        galleryViewModel.state.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
            noItemsFound.isVisible = it
        }

        galleryViewModel.imageList.observe(viewLifecycleOwner) {
            it.onSuccess { gallery ->
                galleryViewModel.galleryAdapter.value?.update(gallery)
            }.onFailure { error ->
                noItemsFound.text = error.message
            }
        }

        if (galleryViewModel.imageList.value !is Ok) {
            galleryViewModel.provideImages()
        }
    }
}