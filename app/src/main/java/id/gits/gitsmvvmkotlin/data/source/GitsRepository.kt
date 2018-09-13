package id.gits.gitsmvvmkotlin.data.source

import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.local.GitsLocalDataSource
import id.gits.gitsmvvmkotlin.data.source.remote.GitsRemoteDataSource

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class GitsRepository(val remoteDataSource: GitsRemoteDataSource,
                          val localDataSource: GitsLocalDataSource) : GitsDataSource {

    var isRemote = false

    override fun remoteMovie(isRemote: Boolean) {
        this.isRemote = isRemote
    }

    override fun saveMovie(movie: Movie) {
        localDataSource.saveMovie(movie)
    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        if (isRemote) {
            getRemoteMovieSource(callback)
        }
    }

    override fun getMovieById(movieId: Int, callback: GitsDataSource.GetMoviesByIdCallback) {
        localDataSource.getMovieById(movieId, object : GitsDataSource.GetMoviesByIdCallback {
            override fun onMovieLoaded(movie: Movie) {
                callback.onMovieLoaded(movie)
            }

            override fun onError(errorMessage: String?) {
                callback.onError(errorMessage)
            }
        })
    }

    private fun getRemoteMovieSource(callback: GitsDataSource.GetMoviesCallback) {
        remoteDataSource.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }

            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }

            override fun onSuccess(movies: List<Movie>) {
                if (movies.isNotEmpty()) {
                    var j = 0

                    for (i in 0 until movies.size) {
                        j = i

                        localDataSource.saveMovie(Movie(movies[i].vote_count, movies[i].id, movies[i].isVideo,
                                movies[i].vote_average, movies[i].title, movies[i].popularity, movies[i].poster_path,
                                movies[i].original_language, movies[i].original_title, movies[i].backdrop_path,
                                movies[i].isAdult, movies[i].overview, movies[i].release_date))

                        if (j == movies.size - 1) {
                            localDataSource.getMovies(object : GitsDataSource.GetMoviesCallback {
                                override fun onShowProgressDialog() {

                                }

                                override fun onHideProgressDialog() {
                                    callback.onHideProgressDialog()
                                }

                                override fun onSuccess(data: List<Movie>) {
                                    callback.onSuccess(data)
                                }

                                override fun onFinish() {
                                    callback.onFinish()
                                }

                                override fun onFailed(statusCode: Int, errorMessage: String) {
                                    callback.onFailed(statusCode, errorMessage)
                                }
                            })
                        }
                    }
                }
            }

            override fun onFinish() {
                callback.onFinish()
            }

            override fun onFailed(statusCode: Int, errorMessage: String) {
                callback.onFailed(statusCode, errorMessage)
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
        fun getInstance(gitsRemoteDataSource: GitsRemoteDataSource, gitsLocalDataSource: GitsLocalDataSource) =
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