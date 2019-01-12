package co.ajsf.kodeinapp.photoslist.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.ajsf.kodeinapp.R
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.view.load
import kotlinx.android.synthetic.main.item_photo.view.*
import java.text.SimpleDateFormat
import java.util.*

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photos: MutableList<Photo> = arrayListOf()
    var onItemClick: ((String) -> Unit)? = null

    fun addPics(photos: List<Photo>): Unit {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class ViewHolder(view: View, private val onItemClick: ((String) -> Unit)?) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {
            with(itemView) {
                cell.setOnClickListener { onItemClick?.invoke(photo.id) }
                picture.load(photo.url)
                title.text = photo.author
                unsplashLink.text = itemView.resources.getString(R.string.unsplash)
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(photo.created_at)
                publishedAt.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date)
                if (photo.description.isNullOrEmpty()) {
                    hashtags.visibility = View.GONE
                } else {
                    hashtags.visibility = View.VISIBLE
                    hashtags.text = photo.description
                }
            }
        }
    }
}