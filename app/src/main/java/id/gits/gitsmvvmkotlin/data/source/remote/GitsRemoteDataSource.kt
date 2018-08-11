package id.gits.gitsmvvmkotlin.data.source.remote

import id.gits.gitsmvvmkotlin.base.BaseApiModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

object GitsRemoteDataSource : GitsDataSource {

    private val apiService = GitsApiService.create()

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        apiService.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                    override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                        callback.onMoviesLoaded(model.results)
                    }

                    override fun onFailure(code: Int, errorMessage: String) {
                        callback.onError("$code: $errorMessage")
                    }

                    override fun onFinish() {
                        // TODO: nothing?
                    }
                })
    }

    override fun getMovieById(movieId: Int, callback: GitsDataSource.GetMoviesByIdCallback) {
        // Tidak digunakan
    }

    override fun remoteMovie(isRemote: Boolean) {
        // Tidak digunakan
    }

    override fun saveMovie(movie: Movie) {
        // Tidak digunakan
    }
}