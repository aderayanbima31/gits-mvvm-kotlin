package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsDataSource {

    fun getMovies(callback: GetMoviesCallback)

    fun getMovieById(movieId: Int, callback: GetMoviesByIdCallback)

    fun saveMovie(movie: Movie)

    fun remoteMovie(isRemote: Boolean)

    interface GetMoviesCallback : BaseResponseCallback<List<Movie>>

    interface GetMoviesByIdCallback {
        fun onMovieLoaded(movie: Movie)
        fun onError(errorMessage: String?)
    }

    /**
     * Base callback supaya rapih aja
     */
    interface BaseResponseCallback<T> {
        fun onShowProgressDialog()
        fun onHideProgressDialog()
        fun onSuccess(data: T)
        fun onFinish()
        fun onFailed(statusCode: Int, errorMessage: String)
    }
}