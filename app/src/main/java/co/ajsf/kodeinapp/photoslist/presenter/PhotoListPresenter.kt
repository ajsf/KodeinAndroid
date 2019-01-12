package co.ajsf.kodeinapp.photoslist.presenter

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.interactor.Invoker
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.presenter.BasePresenter
import co.ajsf.kodeinapp.photoslist.domain.interactor.GetPhotos

class PhotoListPresenter(
    private val invoker: Invoker,
    private val getPhotos: GetPhotos
    //private val navigator: Navigator
) : BasePresenter<PhotoListPresenter.View>() {

    interface View : BasePresenter.View {
        fun renderPhotos(photos: List<Photo>)
        fun displayLoadingPhotosError()
    }

    override fun resume(view: View) {
        super.resume(view)
        loadPhotos()
    }

    private fun loadPhotos() {
        val params = GetPhotos.Params(page = 1, query = "landscape")
        invoker.execute(getPhotos, params, ::onPhotosArrived)
    }

    private fun onPhotosArrived(result: Either<Error, List<Photo>>) {
        result.fold(
            ifLeft = {
                view?.displayLoadingPhotosError()
            },
            ifRight = {
                view?.renderPhotos(it)
            })
    }

    fun onPhotoClick(photoId: String) {
        //navigator.goToDetail(photoId)
    }
}
