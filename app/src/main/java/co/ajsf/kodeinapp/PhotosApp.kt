package co.ajsf.kodeinapp

import android.app.Application
import androidx.annotation.VisibleForTesting
import co.ajsf.kodeinapp.common.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class PhotosApp : Application(), KodeinAware {

    @VisibleForTesting
    var overrideBindings: Kodein.MainBuilder.() -> Unit = {}

    override val kodein: Kodein = Kodein.lazy {
        import(appModule(applicationContext))
    }
}