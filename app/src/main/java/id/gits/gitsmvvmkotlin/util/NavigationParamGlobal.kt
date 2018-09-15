package id.gits.gitsmvvmkotlin.util

import android.support.v7.app.AppCompatActivity

/**
 * NavigationParamGlobal is a data class that using for router helper.
 *
 * @param destinationPage => Destination Page (AppCompatActivity, ex => MainActivity())
 * @param param => Param string for data bucket. Using gson and convert it to String
 */
data class NavigationParamGlobal(val destinationPage: AppCompatActivity, var key: String,
                                 var param: String)