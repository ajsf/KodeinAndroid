package co.ajsf.kodeinapp.detail.description.di

import co.ajsf.kodeinapp.detail.description.DescriptionPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

fun descriptionFragmentModule(description: String) = Kodein.Module("descriptionFragmentModule") {
    bind<DescriptionPresenter>() with provider { DescriptionPresenter(description) }
}