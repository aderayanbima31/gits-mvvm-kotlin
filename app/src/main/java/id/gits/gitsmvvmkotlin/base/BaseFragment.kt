package id.gits.gitsmvvmkotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class BaseFragment : Fragment() {

    lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = (activity as BaseActivity)
    }
}