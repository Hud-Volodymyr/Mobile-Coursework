# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №7<br />
з дисципліни<br />
“Розроблення клієнтських додатків для мобільних платформ”<br />
</p>
<p align="right">
Виконав:<br />
студент групи ІП-84<br />
ЗК ІП-8405<br />
Гудь Володимир<br />
</p>

----------------------------------------------------------------------------------------------------------------

<p align="center">
  Варіант = 05 mod 2 + 1 = 2 
</p>

----------------------------------------------------------------------------------------------------------------

<p align="center">
<b>Приклад роботи додатка<b><br />
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/no_internet_books.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/internet_search_books.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/no_internet_books_saved.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/no_internet_images.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/internet_images.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab7/images/no_internet_images_cached.jpg?raw=true"/>
</p>
  
----------------------------------------------------------------------------------------------------------------

<p>
<b>Приклад коду галереї<b><br />
</p>
  
``` kotlin
package ua.kpi.comsys.ip8405.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
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

    /*private fun addFloatingImageActionClickListener(root: View) {
        val addImageButton = root.findViewById<ImageButton>(R.id.imageCreatorButton)
        addImageButton.setOnClickListener {
            chooseImageGallery()
        }
    }*/

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            galleryViewModel.galleryAdapter.value?.addImage(Image(data?.data.toString()))
            Log.d("IMAGEADD", "Got it")
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
        if (galleryViewModel.dataSourcePicker == null) {
            galleryViewModel.dataSourcePicker = initializePicker("19193969-87191e5db266905fe8936d565", requireActivity().applicationContext)
        }
        val root = getView()
        val recyclerView = root?.findViewById<RecyclerView>(R.id.galleryRecyclerView)
        progressBar = view.findViewById(R.id.progress_bar)!!
        noItemsFound = view.findViewById(R.id.not_found)!!
        recyclerView?.layoutManager = GalleryLayoutManager()
        if (galleryViewModel.galleryAdapter.value == null) {
            createGalleryAdapter()
        }
        recyclerView?.adapter = galleryViewModel.galleryAdapter.value
        /*if (root != null) {
            addFloatingImageActionClickListener(root)
        }*/

        progressBar.isIndeterminate = true
        galleryViewModel.state.observe(viewLifecycleOwner) {
            progressBar.isVisible = it.first()
            noItemsFound.isVisible = it.last()
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
```

<p>
<b>Висновок<b><br />
</p>

В даній лабораторній роботі ми навчилися працювати з різними джерелави даних одночасно, а також ознайомилися з технологією збереження даних на платформі андоїд SQLite.
