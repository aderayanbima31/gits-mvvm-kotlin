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

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        GitsApiService.getApiService
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { callback.onShowProgressDialog() }
                .doOnTerminate { callback.onHideProgressDialog() }
                .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                    override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                        callback.onSuccess(model.results!!)

                        // Sample validation response from API
                        // if (model.code == 200) {
                        // if (model.model.isNotEmpty() || model.data != null) {
                        // Masuk ke callback error (error code, error message dari server. Misal : model.message)
                        // } else {
                        // Masuk ke callback error (error code, error message dari server. Misal : model.message)
                        // }
                        // } else {
                        // Masuk ke callback error (error code, error message dari server. Misal : model.message)
                        // }
                    }

                    override fun onFailure(code: Int, errorMessage: String) {
                        callback.onFailed(code, errorMessage)
                    }

                    override fun onFinish() {
                        callback.onFinish()
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