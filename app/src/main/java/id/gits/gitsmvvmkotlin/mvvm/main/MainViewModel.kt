package id.gits.gitsmvvmkotlin.mvvm.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.annotation.StringRes
import android.util.Log
import com.google.gson.Gson
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class MainViewModel(context: Application, private val gitsRepository: GitsRepository) : AndroidViewModel(context) {

    var movieListLive = MutableLiveData<List<Movie>>()
    val snackBarMessageRemote = SingleLiveEvent<String>()
    val snackBarMessage = SingleLiveEvent<Int>()
    var showProgress = MutableLiveData<Boolean>()
    var movieId = MutableLiveData<Int>()

    internal val openDetailMovie = SingleLiveEvent<Movie>()

    //==============================================================================================

    /**
     * Call this function for first init
     */
    fun start() {
        val isRemote = true

        getMovies(isRemote)
    }

    fun setMovieId(id: Int){
        movieId.value = id
    }

    fun getMovieIds() : MutableLiveData<Int> {
        return movieId
    }

    /**
     * Get movie list from API
     */
    private fun getMovies(isRemote: Boolean) {
        showProgress.value = true

        if (isRemote) {
            gitsRepository.remoteMovie(isRemote)
        }

        gitsRepository.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>?) {
                if (movies != null) {
                    movieListLive.postValue(movies)
                    showProgress.value = false
                }
            }

            override fun onDataNotAvailable() {
                showSnackbarMessage(R.string.data_is_empty)
            }

            override fun onError(errorMessage: String?) {
                if (errorMessage != null) {
                    showSnackbarMessage(errorMessage)
                }
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