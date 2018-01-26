package id.gits.gitsmvvmkotlin.mvvm.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.databinding.MainFragmentBinding

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class MainFragment : BaseFragment() {

    private lateinit var viewBinding: MainFragmentBinding

    private lateinit var mainAdapter: MainAdapter

    //==============================================================================================

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewBinding = MainFragmentBinding.inflate(inflater!!, container, false).apply {

            viewModel = (activity as MainActivity).obtainViewModel()

        }

        return viewBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupMovies()
    }

    override fun onResume() {
        super.onResume()

        showProgressDialog(true)

        viewBinding.viewModel?.start()

        showProgressDialog(false)

    }

    /**
     * Do setup movie list to recyclerview after getting data from API
     */
    private fun setupMovies() {

        val viewModel = viewBinding.viewModel

        if (viewModel != null) {

            mainAdapter = MainAdapter(viewModel.movieList, viewModel)

            viewBinding.recyclerviewMain.adapter = mainAdapter

        }
    }

    companion object {

        fun newInstance() = MainFragment().apply {

        }
    }
}