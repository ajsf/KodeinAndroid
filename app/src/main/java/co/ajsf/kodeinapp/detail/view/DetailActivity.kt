package co.ajsf.kodeinapp.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import co.ajsf.kodeinapp.R
import co.ajsf.kodeinapp.common.di.InjectedActivity
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.view.getBitmapUri
import co.ajsf.kodeinapp.detail.description.DescriptionFragment
import co.ajsf.kodeinapp.detail.di.detailActivityModule
import co.ajsf.kodeinapp.detail.presenter.DetailPresenter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : InjectedActivity(), DetailPresenter.View {

    private val presenter by instance<DetailPresenter>()
    private val photoId by lazy {
        intent?.extras?.getString(EXTRA_ID) ?: NO_ID
    }

    override fun activityModule() = Kodein.Module("detailActivityModule") {
        import(detailActivityModule(photoId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        setupShareButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    private fun setupShareButton() {
        fab.setOnClickListener {
            presenter.onShareButtonClicked()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showShareChooser() {
        val shareIntent = Intent()
        val share = picture.getBitmapUri()
        shareIntent.action = "android.intent.action.SEND"
        shareIntent.putExtra("android.intent.extra.STREAM", share)
        shareIntent.type = "image/jpeg"
        startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    override fun renderPhoto(photo: Photo) {
        Picasso.get()
            .load(photo.url)
            .into(picture)

        content.visibility = View.VISIBLE
        titleView.text = photo.author
        unsplashLink.text = getString(R.string.unsplash)

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(photo.created_at)
        publishedAt.text = SimpleDateFormat("yyy-MM-dd HH:mm", Locale.getDefault()).format(date)
        loadDescriptionFragment(photo.description)
    }

    private fun loadDescriptionFragment(description: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        val descriptionText = description ?: getString(R.string.no_description)
        transaction.replace(R.id.fragmentContainer, DescriptionFragment.newInstance(descriptionText))
        transaction.commit()
    }

    override fun displayLoadingPhotoError() {
        Snackbar.make(toolbar, R.string.loading_photo_error, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val NO_ID = "NO_ID"

        fun getIntent(source: Context, id: String): Intent {
            val intent = Intent(source, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}