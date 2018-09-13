package id.gits.gitsmvvmkotlin.mvvm.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import com.google.gson.Gson
import id.co.gits.gitsbase.BaseViewModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class MainViewModel(context: Application, private val gitsRepository: GitsRepository) :
        BaseViewModel(context) {

    var movieListLive = MutableLiveData<List<Movie>>()
    val snackBarMessageRemote = SingleLiveEvent<String>()
    val snackBarMessage = SingleLiveEvent<Int>()
    var showProgress = MutableLiveData<Boolean>()
    var movieId = MutableLiveData<Int>()

    internal val openDetailMovie = SingleLiveEvent<Movie>()

    fun start() {
        val isRemote = true

        getMovies(isRemote)
    }

    fun setMovieId(id: Int) {
        movieId.value = id
    }

    fun getMovieIds(): MutableLiveData<Int> {
        return movieId
    }

    private fun getMovies(isRemote: Boolean) {
        showProgress.value = true

        if (isRemote) {
            gitsRepository.remoteMovie(isRemote)
        }

        gitsRepository.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onShowProgressDialog() {
                showProgress.value = true
            }

            override fun onHideProgressDialog() {
                showProgress.value = false
            }

            override fun onSuccess(data: List<Movie>) {
                movieListLive.postValue(data)
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String) {
                snackBarMessageRemote.value = errorMessage
            }
        })
    }

    /**
     * Show message response from API
     * @param mMessage
     */
    fun showSnackbarMessage(mMessage: String) {
        snackBarMessageRemote.value = mMessage
    }

    /**
     * Show message response from Local resource
     * @param mMessage
     */
    private fun showSnackbarMessage(@StringRes mMessage: Int) {
        snackBarMessage.value = mMessage
    }
}