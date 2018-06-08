package id.gits.gitsmvvmkotlin

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class GitsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug (bridge) tools
        Stetho.initializeWithDefaults(this)
    }
}