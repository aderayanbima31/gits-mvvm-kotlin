package id.co.gits.gitsbase

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.util.Log
import id.gits.gitsmvvmkotlin.util.NavigationParamGlobal
import id.gits.gitsmvvmkotlin.util.SingleLiveEvent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var eventShowProgress = SingleLiveEvent<Boolean>()
    var eventGlobalMessage = SingleLiveEvent<String>()
    var eventNavigationPage = SingleLiveEvent<NavigationParamGlobal>()

    var verticalList = ObservableField(0)
    var horizontalList = ObservableField(1)

    fun showLogDebug(TAG: String, data: String) = Log.d(TAG, data)
    fun showLogVerbose(TAG: String, data: String) = Log.v(TAG, data)
    fun showLogError(TAG: String, errorMessage: String) = Log.e(TAG, errorMessage)
}