package co.ajsf.kodeinapp.common.router

import android.content.Context
import co.ajsf.kodeinapp.detail.view.DetailActivity

class PhotoAppNavigator(private val context: Context) : Navigator {
    override fun goToDetail(photoId: String) {
        context.startActivity(DetailActivity.getIntent(context, photoId))
    }
}