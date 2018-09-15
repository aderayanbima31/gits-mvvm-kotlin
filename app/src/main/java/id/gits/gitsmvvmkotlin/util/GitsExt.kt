package id.gits.gitsmvvmkotlin.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.replaceFragmentInActivityWithBackStack(fragment: Fragment, frameId: Int,
                                                             TAG: String?) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
        addToBackStack(TAG)
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun View.setupSnackbar(lifecycleOwner: LifecycleOwner,
                       snackbarMessageLiveEvent: SingleLiveEvent<String>, timeLength: Int) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbar(it, timeLength) }
    })
}

fun Fragment.startActivityFromFragment(context: Context, navigationParamGlobal: NavigationParamGlobal) {
    startActivity(Intent(context, navigationParamGlobal.destinationPage::class.java)
            .putExtra(navigationParamGlobal.key, navigationParamGlobal.param))
}

fun AppCompatActivity.startActivityFromActivity(context: Context, navigationParamGlobal: NavigationParamGlobal) {
    startActivity(Intent(context, navigationParamGlobal.destinationPage::class.java)
            .putExtra(navigationParamGlobal.key, navigationParamGlobal.param))
}

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }