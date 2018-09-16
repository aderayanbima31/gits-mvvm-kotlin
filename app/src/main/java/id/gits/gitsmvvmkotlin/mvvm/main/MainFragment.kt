package id.gits.gitsmvvmkotlin.mvvm.main

import android.arch.lifecycle.Observer
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
import id.gits.gitsmvvmkotlin.util.putArgs
import id.gits.gitsmvvmkotlin.util.showToast
import id.gits.gitsmvvmkotlin.util.startNewActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupMovies()
        setupViewListener()
    }

    override fun onMovieClicked(movie: Movie) {
        (activity as MainActivity).apply {
            showToast(context!!, movie.original_title!!)
            startNewActivity(context!!,
                    NavigationParamGlobal(MainDetailActivity(), GitsHelper.Const.EXTRA_GLOBAL,
                            movie.id.toString()))
        }
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
            recycler_main.apply {
                adapter!!.notifyItemRangeRemoved(0,
                        adapter!!.itemCount)
                viewModel.start()
                swipe_main.isRefreshing = false
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment().putArgs { }
    }
}