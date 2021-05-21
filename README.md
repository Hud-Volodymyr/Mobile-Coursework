# Mobile-Coursework
A coursework for mobile developement in Kyiv Politechnical Institute. 3rd-year.

----------------------------------------------------------------------------------------------------------------

<p align= "center">
ЗВІТ
НАЦІОНАЛЬНИЙ ТЕХНІЧНИЙ УНІВЕРСИТЕТ УКРАЇНИ<br />
“КИЇВСЬКИЙ ПОЛІТЕХНІЧНИЙ ІНСТИТУТ ІМЕНІ ІГОРЯ СІКОРСЬКОГО”<br />
Факультет інформатики та обчислювальної техніки<br />
Кафедра обчислювальної техніки<br />
Лабораторна робота №5<br />
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
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab5/images/gallery_images.jpgraw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab5/images/gallery_images_scrolled.jpg?raw=true"/>
<img src="https://github.com/Hud-Volodymyr/Mobile-Coursework/blob/lab5/images/gallery_images_bottom.jpg?raw=true"/>
</p>

----------------------------------------------------------------------------------------------------------------

<p>
<b>Приклад коду галереї<b><br />
</p>
  
``` kotlin
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
```
<p>
<b>Висновок<b><br />
</p>

В даній лабораторній роботі ми навчилися працювати з зображеннями, та менеджментом власностворених макетів.
