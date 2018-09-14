package id.gits.gitsmvvmkotlin.mvvm.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.databinding.MainActivityBinding
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity() {

    private lateinit var viewMainBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMainBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewMainBinding.apply {
            setupToolbar()
            setupMainFragment()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        txt_toolbar_title.text = resources.getString(R.string.app_name)
    }

    private fun setupMainFragment() {
        replaceFragmentInActivity(MainFragment.newInstance(), R.id.frame_main_content)
    }

    fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)
}
