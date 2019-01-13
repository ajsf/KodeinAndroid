package co.ajsf.kodeinapp.detail.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.ajsf.kodeinapp.R
import co.ajsf.kodeinapp.detail.description.di.descriptionFragmentModule
import kotlinx.android.synthetic.main.fragment_description.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class DescriptionFragment : Fragment(), KodeinAware, DescriptionPresenter.View {

    private val presenter by instance<DescriptionPresenter>()

    override val kodein: Kodein = Kodein.lazy {
        val activityKodein by closestKodein(context!!)
        extend(activityKodein)
        import(descriptionFragmentModule(arguments?.getString(EXTRA_TEXT) ?: ""))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_description, container, false)

    override fun onResume() {
        super.onResume()
        presenter.resume(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun renderDescription(descriptionText: String) {
        description.text = descriptionText
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    companion object {
        private const val EXTRA_TEXT = "EXTRA_DESCRIPTION_TEXT"

        fun newInstance(description: String): DescriptionFragment =
            DescriptionFragment().apply {
                arguments = Bundle().apply { putString(EXTRA_TEXT, description) }
            }
    }
}