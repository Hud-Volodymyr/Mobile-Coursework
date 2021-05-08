package ua.kpi.comsys.ip8405.ui.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8405.R
import com.squareup.picasso.Picasso

class GalleryAdapter(private val imageList: MutableList<Uri>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){
    inner class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(uri: Uri) {
            val imageView = itemView.findViewById<ImageView>(R.id.image_view)
            Picasso.get().load(uri).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val galleryLayout = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return GalleryViewHolder(galleryLayout)
    }
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(imageList[position])
    }


    override fun getItemCount(): Int {
        return imageList.size
    }

    fun addImage(image: Uri?) {
        if (image == null) return
        imageList.add(image)
        notifyDataSetChanged()
    }
}