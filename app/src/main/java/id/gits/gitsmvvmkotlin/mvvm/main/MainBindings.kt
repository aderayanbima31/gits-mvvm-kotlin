package id.gits.gitsmvvmkotlin.mvvm.main

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import id.gits.gitsmvvmkotlin.data.model.Movie

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

object MainBindings {

    @BindingAdapter("app:movieList")
    @JvmStatic
    fun setMovieList(recyclerView: RecyclerView, movies: List<Movie>) {

        with(recyclerView.adapter as MainAdapter) {

            replaceData(movies)

        }
    }
}