package co.ajsf.kodeinapp.photoslist.di

import co.ajsf.kodeinapp.photoslist.domain.interactor.GetPhotos
import co.ajsf.kodeinapp.photoslist.presenter.PhotoListPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

fun photoListActivityModule() = Kodein.Module("PhotoListActivityModule") {
    bind<GetPhotos>() with provider { GetPhotos(instance()) }
    bind<PhotoListPresenter>() with provider { PhotoListPresenter(instance(), instance()) }
}