package id.gits.gitsmvvmkotlin.mvvm.main

import android.app.Application
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

    var movieListLive = SingleLiveEvent<List<Movie>>()
    val snackBarMessageRemote = SingleLiveEvent<String>()

    internal val openDetailMovie = SingleLiveEvent<Movie>()

    fun start() {
        val isRemote = true

        getMovies(isRemote)
    }

    private fun getMovies(isRemote: Boolean) {
        if (isRemote) {
            gitsRepository.remoteDataSource.remoteMovie(isRemote)
        }

        gitsRepository.remoteDataSource.getMovies(object : GitsDataSource.GetMoviesCallback {
            override fun onShowProgressDialog() {
                eventShowProgress.value = true
            }

            override fun onHideProgressDialog() {
                eventShowProgress.value = false
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
}