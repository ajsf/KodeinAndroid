package co.ajsf.kodeinapp.detail.di

import co.ajsf.kodeinapp.detail.domain.interactor.GetPhoto
import co.ajsf.kodeinapp.detail.presenter.DetailPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

fun detailActivityModule(photoId: String) = Kodein.Module("DetailActivityModule") {
    bind<GetPhoto>() with provider { GetPhoto(instance()) }
    bind<DetailPresenter>() with provider { DetailPresenter(instance(), photoId, instance()) }
}