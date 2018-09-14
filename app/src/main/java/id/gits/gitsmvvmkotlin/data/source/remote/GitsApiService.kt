package id.gits.gitsmvvmkotlin.data.source.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import id.co.gits.gitsdriver.utils.GitsHelper
import id.gits.gitsmvvmkotlin.BuildConfig
import id.gits.gitsmvvmkotlin.GitsApplication
import id.gits.gitsmvvmkotlin.base.BaseApiModel
import id.gits.gitsmvvmkotlin.data.model.Movie
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsApiService {

    @GET("3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=popularity.desc")
    fun getMovies(): Observable<BaseApiModel<List<Movie>>>

    companion object Factory {

        val getApiService: GitsApiService by lazy {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = (5 * 1024 * 1024).toLong()

            val appCache = Cache(GitsApplication.getContext().cacheDir, cacheSize)

            val mClient = if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                        .cache(appCache)
                        .addInterceptor { chain ->
                            // Get the request from the chain.
                            var request = chain.request()

                            /*
                        *  Leveraging the advantage of using Kotlin,
                        *  we initialize the request and change its header depending on whether
                        *  the device is connected to Internet or not.
                        */
                            request = if (GitsHelper.Func.isNetworkAvailable(GitsApplication.getContext())!!)
                            /*
                        *  If there is Internet, get the cache that was stored 5 seconds ago.
                        *  If the cache is older than 5 seconds, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-age' attribute is responsible for this behavior.
                        */
                                request.newBuilder().header("Cache-Control",
                                        "public, max-age=" + 5).build()
                            else
                            /*
                        *  If there is no Internet, get the cache that was stored 7 days ago.
                        *  If the cache is older than 7 days, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-stale' attribute is responsible for this behavior.
                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                        */
                                request.newBuilder().header("Cache-Control",
                                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                            // End of if-else statement

                            // Add the modified request to the chain.
                            chain.proceed(request)
                        }
                        .addInterceptor(mLoggingInterceptor)
                        .addInterceptor(ChuckInterceptor(GitsApplication.getContext()))
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build()
            } else {
                OkHttpClient.Builder()
                        .cache(appCache)
                        .addInterceptor { chain ->
                            var request = chain.request()
                            request = if (GitsHelper.Func.isNetworkAvailable(GitsApplication.getContext())!!)
                                request.newBuilder().header("Cache-Control",
                                        "public, max-age=" + 5).build()
                            else
                                request.newBuilder().header("Cache-Control",
                                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                            chain.proceed(request)
                        }
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build()
            }

            val mRetrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mClient)
                    .build()

            mRetrofit.create(GitsApiService::class.java)
        }
    }
}