package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class GitsRepository(val remoteDataSource: GitsDataSource,
                          val localDataSource: GitsDataSource) : GitsDataSource {

    override fun saveMovieId(movieId: String) {

        remoteDataSource.saveMovieId(movieId)

        localDataSource.saveMovieId(movieId)

    }

    override fun getMovieId(): String {

        return localDataSource.getMovieId()

    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {

        remoteDataSource.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>?) {
                callback.onMoviesLoaded(movies)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError(errorMessage: String?) {
                callback.onError(errorMessage)
            }
        })

    }

    companion object {

        private var INSTANCE: GitsRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param gitsRemoteDataSourcethe backend data source
         * *
         * @return the [GitsRepository] instance
         */
        @JvmStatic
        fun getInstance(gitsRemoteDataSource: GitsDataSource, gitsLocalDataSource: GitsDataSource) =
                INSTANCE ?: synchronized(GitsRepository::class.java) {
                    INSTANCE ?: GitsRepository(gitsRemoteDataSource, gitsLocalDataSource)
                            .also { INSTANCE = it }
                }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}