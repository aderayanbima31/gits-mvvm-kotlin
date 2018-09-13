package id.gits.gitsmvvmkotlin.mvvm.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class MainFragment : BaseFragment() {

    private lateinit var viewBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainActivity).obtainViewModel().apply {
                // Observer view model here

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
    }

    private fun setupViewModel() {
        viewModel = viewBinding.viewModel!!

        viewModel.start()
    }

    private fun setupMovies() {
        recyclerview_main.adapter = MainAdapter(viewModel)
    }

    companion object {
        fun newInstance() = MainFragment().apply {

        }
    }
}