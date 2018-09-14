package id.gits.gitsmvvmkotlin

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import id.co.gits.gitsdriver.utils.GitsHelper

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class GitsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
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