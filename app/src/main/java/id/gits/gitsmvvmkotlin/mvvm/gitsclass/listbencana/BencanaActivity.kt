package id.gits.gitsmvvmkotlin.mvvm.gitsclass.listbencana;

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.util.obtainViewModel
import id.gits.gitsmvvmkotlin.util.replaceFragmentInActivity



class BencanaActivity : AppCompatActivity(), BencanaNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bencana)
        setupFragment()
    }

    // TODO add BencanaViewModel to ViewModelFactory & if template have an error, please reimport obtainViewModel
    fun obtainViewModel(): BencanaViewModel = obtainViewModel(BencanaViewModel::class.java)

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frame_main_content)
        BencanaFragment.newInstance().let {
            // TODO if template have an error, please reimport replaceFragmentInActivity
            replaceFragmentInActivity(it, R.id.frame_main_content)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, BencanaActivity::class.java))
        }
    }
}
