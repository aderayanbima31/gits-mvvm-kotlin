package id.gits.gitsmvvmkotlin.data.source.local

import android.support.annotation.VisibleForTesting
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.local.movie.MovieDao
import id.gits.gitsmvvmkotlin.util.dbhelper.AppExecutors

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class GitsLocalDataSource private constructor(private val appExecutors: AppExecutors,
                                              private val movieDao: MovieDao) : GitsDataSource {

    override fun getMovieById(movieId: Int, callback: GitsDataSource.GetMoviesByIdCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getMovieById(movieId)

            appExecutors.mainThread.execute {
                if (movies == null) {
                    callback.onFailed(GitsHelper.Const.SERVER_CODE_404,
                            GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT)
                } else {
                    callback.onSuccess(movies)
                }
            }
        }
    }

    override fun remoteMovie(isRemote: Boolean) {
        // Not required because the {@link GitsRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    override fun saveMovie(movie: Movie) {
        appExecutors.diskIO.execute {
            movieDao.insertMovie(movie)
        }
    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getAllMovies()

            appExecutors.mainThread.execute {
                if (movies.isEmpty()) {
                    callback.onFailed(GitsHelper.Const.SERVER_CODE_404,
                            GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT)
                } else {
                    callback.onSuccess(movies)
                }
                callback.onHideProgressDialog()
            }
        }
    }

    companion object {

        private var INSTANCE: GitsLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, movieDao: MovieDao): GitsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(GitsLocalDataSource::javaClass) {
                    INSTANCE = GitsLocalDataSource(appExecutors, movieDao)
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