package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsDataSource {

    fun getMovies(callback: GetMoviesCallback)

    fun saveMovie(movie: Movie)

    fun remoteMovie(isRemote: Boolean)

    interface GetMoviesCallback {

        fun onMoviesLoaded(movies: List<Movie>?)

        fun onDataNotAvailable()

        fun onError(errorMessage: String?)
    }
}