package id.gits.gitsmvvmkotlin.mvvm.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.databinding.MainFragmentBinding
import id.gits.gitsmvvmkotlin.mvvm.maindetail.MainDetailActivity
import id.gits.gitsmvvmkotlin.util.NavigationParamGlobal
import id.gits.gitsmvvmkotlin.util.startActivity
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class MainFragment : BaseFragment(), MainItemUserActionListener {

    private lateinit var viewBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainActivity).obtainViewModel().apply {
                openDetailMovie.observe(this@MainFragment, Observer { movie ->
                    onMovieClicked(movie!!)
                })
            }
        }

        viewBinding.let {
            it.viewModel = viewBinding.viewModel
            it.setLifecycleOwner(this@MainFragment)
        }

        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupMovies()
        setupViewListener()
    }

    override fun onMovieClicked(movie: Movie) {
        // Cara ke 1 jika hanya ada satu param yang dilempar
        // val intent = Intent(this@MainFragment.context, MainDetailActivity::class.java)
        // intent.putExtra(GitsHelper.Const.EXTRA_MOVIE_ID, movie.id!!)
        // startActivity(intent.putExtra("", ""))

        // Cara ke 2 jika data yang dikirim banyak
        // Convert data params menjadi string dengan menggunakan gson
        startActivity(context!!, NavigationParamGlobal(MainDetailActivity(), movie.id.toString()))
    }

    private fun setupViewModel() {
        viewModel = viewBinding.viewModel!!

        viewModel.start()
    }

    private fun setupMovies() {
        recycler_main.adapter = MainAdapter(viewModel)
    }

    private fun setupViewListener() {
        swipe_main.setOnRefreshListener {
            viewModel.start()
            swipe_main.isRefreshing = false
        }
    }

    companion object {
        fun newInstance() = MainFragment().apply {

        }
    }
}