package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsDataSource {

    /**
     * This sample function for save movie id to preference
     * @param movieId
     */
    fun saveMovieId(movieId: String)

    /**
     * This sample function for getting movie id from preference
     */
    fun getMovieId(): String

    /**
     * Get all movie list
     * @param callback
     */
    fun getMovies(callback: GetMoviesCallback)

    interface GetMoviesCallback {

        fun onMoviesLoaded(movies: List<Movie>?)

        fun onDataNotAvailable()

        fun onError(errorMessage: String?)
    }
}