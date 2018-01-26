package id.gits.gitsmvvmkotlin.mvvm.main


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.base.BaseActivity
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity

class MainActivity : BaseActivity(), MainItemUserActionListener {

    private lateinit var toolbar: Toolbar

    private lateinit var viewModel: MainViewModel

    //==============================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupToolbar()

        setupFragment()

        setupViewModel()

    }

    /**
     * Do setup toolbar app
     */
    private fun setupToolbar() {}

    /**
     * Do setup fragment main view
     */
    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frame_main_content)
        MainFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.frame_main_content)
        }
    }

    private fun setupViewModel(){
        viewModel = obtainViewModel().apply {

            openDetailMovie.observe(this@MainActivity, Observer { movie ->
                onMovieClicked(movie!!)
            })
        }
    }

    override fun onMovieClicked(movie: Movie) {
        Toast.makeText(mActiviy, movie.original_title, Toast.LENGTH_SHORT).show()
    }

    fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)
}
