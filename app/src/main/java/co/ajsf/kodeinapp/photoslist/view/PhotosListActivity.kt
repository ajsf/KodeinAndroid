package co.ajsf.kodeinapp.photoslist.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import co.ajsf.kodeinapp.R
import co.ajsf.kodeinapp.common.di.InjectedActivity
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.photoslist.di.photoListActivityModule
import co.ajsf.kodeinapp.photoslist.presenter.PhotoListPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_photo_list.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class PhotosListActivity : InjectedActivity(), PhotoListPresenter.View {

    private val presenter by instance<PhotoListPresenter>()
    private val adapter = PhotosAdapter()

    override fun activityModule() = Kodein.Module("photoListActivityModule") {
        import(photoListActivityModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)
        setSupportActionBar(toolbar)
        setupPhotosList()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_photo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupPhotosList() {
        photoList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        photoList.layoutManager = layoutManager
        photoList.adapter = adapter
        adapter.onItemClick = ::onItemClick
    }

    private fun onItemClick(id: String): Unit = presenter.onPhotoClick(id)

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun renderPhotos(photos: List<Photo>) {
        adapter.addPics(photos)
    }

    override fun displayLoadingPhotosError() {
        Snackbar.make(toolbar, R.string.loading_photos_error, Snackbar.LENGTH_SHORT).show()
    }
}