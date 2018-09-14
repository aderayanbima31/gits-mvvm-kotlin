package id.gits.gitsmvvmkotlin.mvvm.maindetail

import android.app.Application
import com.google.gson.Gson
import id.co.gits.gitsbase.BaseViewModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

class MainDetailViewModel(context: Application,
                          private val gitsRepository: GitsRepository) : BaseViewModel(context) {

    val movieTitle = SingleLiveEvent<String>()
    var movieRating = SingleLiveEvent<String>()
    var movieDateRelease = SingleLiveEvent<String>()
    var movieDescription = SingleLiveEvent<String>()
    var movieImageBackdropUrl = SingleLiveEvent<String>()
    var movieImagePosterUrl = SingleLiveEvent<String>()

    fun getMovieById(movieId: Int) {
        gitsRepository.localDataSource.getMovieById(movieId, object : GitsDataSource.GetMoviesByIdCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: Movie) {
                movieTitle.value = data.title
                movieRating.value = data.vote_average.toString()
                movieDescription.value = data.overview
                movieDateRelease.value = data.release_date
                movieImageBackdropUrl.value = data.backdrop_path
                movieImagePosterUrl.value = data.poster_path
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String) {
                eventGlobalMessage.value = errorMessage
            }
        })
    }
}
