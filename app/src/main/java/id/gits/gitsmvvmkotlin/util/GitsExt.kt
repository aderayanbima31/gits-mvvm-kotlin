package id.gits.gitsmvvmkotlin.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.R

/**
 * Created by irfanirawansukirman on 26/01/18.
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

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun RecyclerView.verticalListStyle() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.horizontalListStyle() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun View.showSnackbarWithCustomColor(view: android.view.View, message: String,
                                textColor: Int, backgroundColor: Int,
                                duration: Int) {
    val finalMessage = if (TextUtils.isEmpty(message)) {
        GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT
    } else {
        message
    }

    val finalDuration = if (duration == 0) {
        GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT
    } else {
        duration
    }

    val finalTextColor = if (textColor == 0) {
        ContextCompat.getColor(view.context, R.color.mainWhite)
    } else {
        textColor
    }

    val finalBackgroundColor = if (textColor == 0) {
        ContextCompat.getColor(view.context, R.color.greyBackgroundDefault)
    } else {
        backgroundColor
    }

    val snackView = Snackbar.make(view, finalMessage, finalDuration)
    snackView.setActionTextColor(finalTextColor)
    snackView.view.setBackgroundColor(finalBackgroundColor)
    snackView.show()
}

fun View.showSnackbarDefault(view: android.view.View, message: String, duration: Int) {
    val finalMessage = if (TextUtils.isEmpty(message)) {
        GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT
    } else {
        message
    }

    val finalDuration = if (duration == 0) {
        GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT
    } else {
        duration
    }

    Snackbar.make(view, finalMessage, finalDuration).show()
}

fun View.setCustomFont(view: android.view.View, fontName: String): Typeface = Typeface
        .createFromAsset(view.context.assets, "fonts/$fontName")

fun View.setupSnackbar(lifecycleOwner: LifecycleOwner,
                       snackbarMessageLiveEvent: SingleLiveEvent<String>, timeLength: Int) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbarDefault(this, it, timeLength) }
    })
}

fun AppCompatActivity.showToast(context: Context, message: String) {
    Toast.makeText(context, if (TextUtils.isEmpty(message))
        GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT else message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.startNewActivity(context: Context, navigationParamGlobal: NavigationParamGlobal) {
    startActivity(Intent(context, navigationParamGlobal.destinationPage::class.java)
            .putExtra(navigationParamGlobal.key, navigationParamGlobal.param))
}

fun AppCompatActivity.transparentStatusBar(decorView: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }