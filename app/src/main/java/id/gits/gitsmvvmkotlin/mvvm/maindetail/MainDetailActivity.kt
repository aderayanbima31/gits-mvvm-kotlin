package id.gits.gitsmvvmkotlin.mvvm.maindetail

import android.databinding.DataBindingUtil
import android.os.Bundle
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.MainDetailActivityBinding
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity
import id.gits.gitsmvvmkotlin.util.transparentStatusBar

class MainDetailActivity : BaseActivity() {

    private lateinit var viewBinding: MainDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.main_detail_activity)
        viewBinding.apply {
            transparentStatusBar(window.decorView)

            val params = intent.getStringExtra(GitsHelper.Const.EXTRA_GLOBAL)

            if (savedInstanceState == null) {
                replaceFragmentInActivity(MainDetailFragment.newInstance(params.toInt()),
                        R.id.frame_container)
            }
        }
    }

    fun obtainViewModel(): MainDetailViewModel = obtainViewModel(MainDetailViewModel::class.java)
}
