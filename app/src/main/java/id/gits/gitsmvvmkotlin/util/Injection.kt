package id.gits.gitsmvvmkotlin.util

import android.content.Context
import android.preference.PreferenceManager
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.data.source.local.GitsLocalDataSource
import id.gits.gitsmvvmkotlin.data.source.remote.GitsRemoteDataSource

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

object Injection {

    fun provideGitsRepository(context: Context) = GitsRepository.getInstance(GitsRemoteDataSource,
            GitsLocalDataSource.getInstance(PreferenceManager.getDefaultSharedPreferences(context)))

}