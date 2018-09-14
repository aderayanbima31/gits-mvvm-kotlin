package id.gits.gitsmvvmkotlin.mvvm.maindetail

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.view.View
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.MainDetailActivityBinding
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity


class MainDetailActivity : BaseActivity() {

    private lateinit var viewModel: MainDetailViewModel
    private lateinit var viewBinding: MainDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.main_detail_activity)
        viewBinding.apply {
            // Hide navigation bar
            if (Build.VERSION.SDK_INT >= 19) {
                val v = window.decorView
                v.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }

            val movieId = intent.getStringExtra(GitsHelper.Const.EXTRA_GLOBAL)

            if (savedInstanceState == null) {
                replaceFragmentInActivity(MainDetailFragment.newInstance(movieId.toInt()),
                        R.id.frame_container)
            }

            setupViewModel()
        }
    }

    private fun setupViewModel() {
        viewModel = obtainViewModel().apply { }
    }

    fun obtainViewModel(): MainDetailViewModel = obtainViewModel(MainDetailViewModel::class.java)
}
