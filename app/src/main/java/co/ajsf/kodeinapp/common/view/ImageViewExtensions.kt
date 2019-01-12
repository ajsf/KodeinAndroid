package co.ajsf.kodeinapp.common.view

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso

fun ImageView.load(url: String, @DrawableRes placeHolderRes: Int = 0) {
    Picasso.get()
        .load(url)
        .also {
            if (placeHolderRes != 0) it.placeholder(placeHolderRes)
        }
        .into(this)
}