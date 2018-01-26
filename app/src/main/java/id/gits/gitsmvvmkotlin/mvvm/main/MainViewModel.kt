package id.gits.gitsmvvmkotlin.mvvm.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.annotation.StringRes
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class MainViewModel(context: Application, private val gitsRepository: GitsRepository) : AndroidViewModel(context) {

    val movieList: ObservableList<Movie> = ObservableArrayList()

    val snackBarMessageRemote = SingleLiveEvent<String>()

    val snackBarMessage = SingleLiveEvent<Int>()

    internal val openDetailMovie = SingleLiveEvent<Movie>()

    //==============================================================================================

    /**
     * Call this function for first init
     */
    fun start() {
        getMovies()
    }

    /**
     * Get movie list from API
     */
    private fun getMovies() {
        gitsRepository.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>?) {
                with(movieList) {
                    clear()
                    addAll(movies!!)
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