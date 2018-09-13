package id.gits.gitsmvvmkotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class BaseActivity : AppCompatActivity() {

    lateinit var mActiviy: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActiviy = this
    }
}