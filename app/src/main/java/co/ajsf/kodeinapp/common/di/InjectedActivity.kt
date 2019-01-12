package co.ajsf.kodeinapp.common.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import co.ajsf.kodeinapp.PhotosApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class InjectedActivity : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(appKodein)
        import(baseActivityModule(this@InjectedActivity), allowOverride = true)
        import(activityModule())
        (app().overrideBindings)()
    }

    open fun activityModule() = Kodein.Module("activityModule") {}

}

fun Activity.app() = applicationContext as PhotosApp