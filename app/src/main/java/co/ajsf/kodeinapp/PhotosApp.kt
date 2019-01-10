package co.ajsf.kodeinapp

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class PhotosApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein {

    }

}