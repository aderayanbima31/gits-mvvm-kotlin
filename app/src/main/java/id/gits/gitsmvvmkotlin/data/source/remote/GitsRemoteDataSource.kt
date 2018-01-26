package id.gits.gitsmvvmkotlin.data.source.remote

import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

object GitsRemoteDataSource : GitsDataSource {

    private val apiService = GitsApiService.create()

    private lateinit var movieId: String

    //==============================================================================================

    override fun saveMovieId(movieId: String) {
        this.movieId = movieId
    }

    override fun getMovieId(): String {
        return movieId
    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {

        apiService.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ results ->
                    run {
                        if (results.results!!.isNotEmpty()) {
                            callback.onMoviesLoaded(results.results)
                        } else {
                            callback.onDataNotAvailable()
                        }
                    }
                }, { error ->
                    callback.onError(error.message)
                })

    }
}