package id.gits.gitsmvvmkotlin.data.source.remote

import id.gits.gitsmvvmkotlin.BuildConfig
import id.gits.gitsmvvmkotlin.data.model.eventsnext.EventsNextDao
import id.gits.gitsmvvmkotlin.data.model.eventspast.Event
import id.gits.gitsmvvmkotlin.data.model.eventspast.EventsPastDao
import retrofit2.http.GET
import java.util.*
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ScheduleApiService {


//    @GET("api/v1/json/1/eventspastleague.php?id=4328")
//    fun getEventsPast(): Observable<EventsPastDao>
//
//    @GET("api/v1/json/1/eventsnextleague.php?id=4328")
//    fun getEventsNext(): Observable<EventsNextDao>
//
//
//    companion object Factory{
//
//        fun createKADE(): ScheduleApiService {
//
//            val mLoggingInterceptor = HttpLoggingInterceptor()
//            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//
//            val mClient = OkHttpClient.Builder()
//                    .addInterceptor(mLoggingInterceptor)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .build()
//
//            /**
//             * Un-comment when unit test
//             */
////            val resource = OkHttp3IdlingResource.create("OkHttp", client)
////            IdlingRegistry.getInstance().register(resource)
//
//            val builder = Retrofit.Builder()
//                    .baseUrl(BuildConfig.BASE_URL_KADE)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//
//
//            if (BuildConfig.FLAVOR == "dev" || BuildConfig.FLAVOR == "stg") {
//                builder.client(mClient)
//            }
//            val mRetrofit = builder.build()
//            return mRetrofit.create(ScheduleApiService::class.java)
//        }
//
//
//    }

}