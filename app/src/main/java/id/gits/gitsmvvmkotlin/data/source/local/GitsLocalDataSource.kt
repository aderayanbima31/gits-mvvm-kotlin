package id.gits.gitsmvvmkotlin.data.source.local

import android.content.SharedPreferences
import android.support.annotation.VisibleForTesting
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.util.Preference

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class GitsLocalDataSource private constructor(private val preferences: SharedPreferences) : GitsDataSource {

    override fun saveMovieId(movieId: String) {
        preferences.edit().putString(Preference.KEY, movieId)
    }

    override fun getMovieId(): String = preferences.getString(Preference.KEY, "")

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var INSTANCE: GitsLocalDataSource? = null

        @JvmStatic
        fun getInstance(preferences: SharedPreferences): GitsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(GitsLocalDataSource::javaClass) {
                    INSTANCE = GitsLocalDataSource(preferences)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}