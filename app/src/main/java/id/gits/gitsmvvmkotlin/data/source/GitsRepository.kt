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
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }

            override fun onHideProgressDialog() {
                callback.onHideProgressDialog()
            }

            override fun onSuccess(data: Movie) {
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

    private fun getRemoteMovieSource(callback: GitsDataSource.GetMoviesCallback) {
        remoteDataSource.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onShowProgressDialog() {
                callback.onShowProgressDialog()
            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Movie>) {
                if (data.isNotEmpty()) {
                    for (i in 0 until data.size) {
                        localDataSource.saveMovie(Movie(data[i].vote_count, data[i].id, data[i].isVideo,
                                data[i].vote_average, data[i].title, data[i].popularity, data[i].poster_path,
                                data[i].original_language, data[i].original_title, data[i].backdrop_path,
                                data[i].isAdult, data[i].overview, data[i].release_date))

                        if (i == data.size - 1) {
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

         * @param gitsRemoteDataSource backend data source
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