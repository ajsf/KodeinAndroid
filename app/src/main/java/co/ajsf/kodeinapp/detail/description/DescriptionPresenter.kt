package co.ajsf.kodeinapp.detail.description

import co.ajsf.kodeinapp.common.presenter.BasePresenter

class DescriptionPresenter(private val description: String) : BasePresenter<DescriptionPresenter.View>() {

    interface View : BasePresenter.View {
        fun renderDescription(descriptionText: String): Unit
    }

    override fun resume(view: View) {
        super.resume(view)
        view.renderDescription(description)
    }
}