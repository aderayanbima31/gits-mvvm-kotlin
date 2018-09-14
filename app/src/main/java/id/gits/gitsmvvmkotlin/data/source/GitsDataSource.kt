package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.base.BaseDataSource
import id.gits.gitsmvvmkotlin.base.BaseDataSource.GitsResponseCallback
import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsDataSource: BaseDataSource {

    fun getMovies(callback: GetMoviesCallback)

    fun getMovieById(movieId: Int, callback: GetMoviesByIdCallback)

    fun saveMovie(movie: Movie)

    fun remoteMovie(isRemote: Boolean)

    interface GetMoviesCallback : GitsResponseCallback<List<Movie>>

    interface GetMoviesByIdCallback : GitsResponseCallback<Movie>

}