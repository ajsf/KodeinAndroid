package co.ajsf.kodeinapp.detail.presenter

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.interactor.Invoker
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.presenter.BasePresenter
import co.ajsf.kodeinapp.detail.domain.interactor.GetPhoto

class DetailPresenter(
    private val invoker: Invoker,
    private val photoId: String,
    private val getPhoto: GetPhoto
) : BasePresenter<DetailPresenter.View>() {

    interface View : BasePresenter.View {
        fun renderPhoto(photo: Photo)
        fun displayLoadingPhotoError()
        fun showShareChooser(): Unit
    }

    override fun resume(view: View) {
        super.resume(view)
        loadPhoto()
    }

    private fun loadPhoto() {
        view?.showLoading()
        invoker.execute(getPhoto, photoId, ::onPhotoArrived)
    }

    private fun onPhotoArrived(result: Either<Error, Photo>) {
        result.fold(ifLeft = {
            view?.hideLoading()
            view?.displayLoadingPhotoError()
        }, ifRight = {
            view?.hideLoading()
            view?.renderPhoto(it)
        })
    }

    fun onShareButtonClicked() {
        view?.showShareChooser()
    }
}