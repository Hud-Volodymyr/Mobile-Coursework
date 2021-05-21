package ua.kpi.comsys.ip8405.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8405.R
import com.squareup.picasso.Picasso

class GalleryAdapter(private var imageList: List<Image> = listOf(), private val handler: Picasso) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){
    inner class GalleryViewHolder(view: View, private val handler: Picasso) : RecyclerView.ViewHolder(view) {
        fun bind(image: Image) {
            val imageView = itemView.findViewById<ImageView>(R.id.image_view)
            handler.load(image.largeImageURL).error(R.drawable.ic_nocover).placeholder(R.drawable.ic_nocover).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val galleryLayout = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return GalleryViewHolder(galleryLayout, handler)
    }
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(imageList[position])
    }


    override fun getItemCount(): Int {
        return imageList.size
    }

    fun addImage(image: Image?) {
        if (image == null) return
        imageList += image
        notifyDataSetChanged()
    }

    fun update(newImages: ImageGallery) {
        imageList = newImages.hits
        notifyDataSetChanged()
    }
}