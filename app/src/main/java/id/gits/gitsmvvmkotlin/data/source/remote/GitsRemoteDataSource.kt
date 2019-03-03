package id.gits.gitsmvvmkotlin.data.source.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.gits.gitsmvvmkotlin.base.BaseApiModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.model.eventspast.Event
import id.gits.gitsmvvmkotlin.data.model.eventspast.EventsPastDao
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.util.GitsNullAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
object GitsRemoteDataSource : GitsDataSource {

    //private val mEventsPast = ScheduleApiService.createKADE()

    private val movieListAdapter: Gson by lazy {
        GsonBuilder()
                .registerTypeAdapter(Movie::class.java, GitsNullAdapter())
                .create()
    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        GitsApiService.getApiService
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { callback.onShowProgressDialog() }
                .doOnTerminate { callback.onHideProgressDialog() }
                .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                    override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                        val oldData = model.results
                        val newData = ArrayList<Movie>()

                        for (i in 0 until oldData!!.size) {
                            newData.add(Gson().fromJson(movieListAdapter.toJson(oldData[i]),
                                    Movie::class.java))
                        }

                        callback.onSuccess(newData)

                        // if (model.code == 200) {
                            // if (model.data != null) {
                                // val oldData = model.results
                                // val newData = ArrayList<Movie>()

                                // for (i in 0 until oldData!!.size) {
                                    // newData.add(Gson().fromJson(movieListAdapter.toJson(oldData[i]),
                                            // Movie::class.java))
                                // }

                                // callback.onSuccess(newData)
                            // } else {
                                // callback.onFailed(model.code, model.message)
                            // }
                        // } else {
                            // callback.onFailed(model.code, model.message)
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