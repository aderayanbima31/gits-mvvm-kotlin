package id.gits.gitsmvvmkotlin

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import id.co.gits.gitsdriver.utils.GitsHelper
import io.fabric.sdk.android.Fabric

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class GitsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())

        instance = this

        // Debug
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(GitsHelper.SystemLocale.onAttach(base, "en"))
    }

    companion object {
        lateinit var instance: GitsApplication

        fun getContext(): Context = instance.applicationContext
    }

}